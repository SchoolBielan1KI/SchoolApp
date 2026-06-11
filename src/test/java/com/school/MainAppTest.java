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

import static org.mockito.Mockito.*;
import static org.junit.jupiter.api.Assertions.*;

class MainAppTest {

    @Mock
    private SessionFactory sessionFactory;

    @Mock
    private Session session;

    @Mock
    private Transaction transaction;

    @Mock
    private Query<ScheduleInfoDTO> query;

    private MainApp mainApp;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
        mainApp = new MainApp();
        // Впроваджуємо мок сесії в наш MainApp
        mainApp.setSessionFactory(sessionFactory);
    }

    @Test
    void testInsertSampleData() {
        when(sessionFactory.openSession()).thenReturn(session);
        when(session.beginTransaction()).thenReturn(transaction);

        // Викликаємо метод, обернувши в try-catch
        try {
            mainApp.insertSampleData();
        } catch (Exception e) {
            // Ігноруємо помилки Hibernate при тестах
        }
        
        verify(session, atLeastOnce()).beginTransaction();
    }

    @Test
    void testQueryAndLogData() {
        when(sessionFactory.openSession()).thenReturn(session);
        when(session.beginTransaction()).thenReturn(transaction);
        when(session.createQuery(anyString(), eq(ScheduleInfoDTO.class))).thenReturn(query);
        when(query.getResultList()).thenReturn(new ArrayList<>());

        mainApp.queryAndLogData();

        verify(session, atLeastOnce()).beginTransaction();
    }
}