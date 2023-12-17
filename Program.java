package org.example.javajunior04;

/**
 * Создайте базу данных (например, SchoolDB).
 * В этой базе данных создайте таблицу Course с полями id (ключ), title, и duration.
 * Настройте Hibernate для работы с вашей базой данных.
 * Создайте Java-класс Course, соответствующий таблице Course, с необходимыми аннотациями Hibernate.
 * Используя Hibernate, напишите код для вставки, чтения, обновления и удаления данных в таблице Course.
 * Убедитесь, что каждая операция выполняется в отдельной транзакции.
 */

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.cfg.Configuration;

public class Program {
    public static void main(String[] args) {
        Configuration configuration = new Configuration()
                .addAnnotatedClass(Course.class);

        Course course = new Course("Курс Java", 25);
        Course courseWithDb;

        SessionFactory sessionFactory = configuration.buildSessionFactory();
        try (Session session = sessionFactory.getCurrentSession()) {
            session.beginTransaction();
            // сохранение в БД
            session.persist(course);
            session.getTransaction().commit();
            System.out.println("Добавлено");
        } finally {
            sessionFactory.close();
        }

        configuration = new Configuration().addAnnotatedClass(Course.class);
        sessionFactory = configuration.buildSessionFactory();
        try (Session session = sessionFactory.getCurrentSession()) {
            // из БД
            session.beginTransaction();
            courseWithDb = session.get(Course.class, course.getId());
            session.getTransaction().commit();
            System.out.println("Получено Из БД: " + courseWithDb);
        } finally {
            sessionFactory.close();
        }

        configuration = new Configuration().addAnnotatedClass(Course.class);
        sessionFactory = configuration.buildSessionFactory();
        try (Session session = sessionFactory.getCurrentSession()) {
            // изменение в БД
            courseWithDb.setDuration(6);
            session.beginTransaction();
            session.merge(courseWithDb);
            session.getTransaction().commit();
            System.out.println("Изменено в БД");
        } finally {
            sessionFactory.close();
        }

        configuration = new Configuration().addAnnotatedClass(Course.class);
        sessionFactory = configuration.buildSessionFactory();
        try (Session session = sessionFactory.getCurrentSession()) {
            // удаление из БД
            session.beginTransaction();
            session.remove(courseWithDb);
            session.getTransaction().commit();
            System.out.println("Удалено из БД");
        } finally {
            sessionFactory.close();
        }
    }
}