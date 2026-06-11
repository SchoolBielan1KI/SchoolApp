package com.school;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.hibernate.query.Query;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class MainAppTest {

    @Mock private SessionFactory sessionFactory;
    @Mock private Session session;
    @Mock private Transaction transaction;
    @Mock private Query<ScheduleInfoDTO> query;

    private MainApp mainApp;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
        mainApp = new MainApp();
        mainApp.setSessionFactory(sessionFactory);
        when(sessionFactory.openSession()).thenReturn(session);
        when(session.beginTransaction()).thenReturn(transaction);
    }

    @Test
    void testMainMethod_Coverage() {
        MainApp spyApp = spy(mainApp);
        // Успіх
        assertDoesNotThrow(() -> MainApp.runLogic(spyApp));
        
        // Помилка (trigger catch)
        doThrow(new RuntimeException("Error")).when(spyApp).setup();
        assertDoesNotThrow(() -> MainApp.runLogic(spyApp));
    }

    @Test
    void testShutdown_Branches() {
        MainApp app = new MainApp();
        app.shutdown(); // null branch
        mainApp.shutdown(); // not null branch
        verify(sessionFactory).close();
    }

    @Test
    void testInsertSampleData_Branches() {
        // Успіх
        mainApp.insertSampleData();
        verify(transaction).commit();

        // Помилка (catch + rollback)
        doThrow(new RuntimeException("Fail")).when(session).save(any());
        mainApp.insertSampleData();
        verify(transaction, times(1)).rollback();
    }

    @Test
    void testQueryAndLogData_Branches() {
        when(session.createQuery(anyString(), eq(ScheduleInfoDTO.class))).thenReturn(query);
        
        // Гілка 1: empty
        when(query.getResultList()).thenReturn(new ArrayList<>());
        mainApp.queryAndLogData();

        // Гілка 2: not empty (loop)
        List<ScheduleInfoDTO> list = new ArrayList<>();
        list.add(mock(ScheduleInfoDTO.class));
        when(query.getResultList()).thenReturn(list);
        mainApp.queryAndLogData();

        // Гілка 3: exception (catch + rollback)
        when(query.getResultList()).thenThrow(new RuntimeException("Fail"));
        mainApp.queryAndLogData();
        verify(transaction, times(1)).rollback();
    }
}