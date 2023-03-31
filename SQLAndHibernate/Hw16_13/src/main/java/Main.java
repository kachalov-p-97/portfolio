
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.hibernate.boot.Metadata;
import org.hibernate.boot.MetadataSources;
import org.hibernate.boot.registry.StandardServiceRegistry;
import org.hibernate.boot.registry.StandardServiceRegistryBuilder;

import java.util.List;

public class Main {
    public static void main(String[] args) {


        try {
            StandardServiceRegistry registry = new StandardServiceRegistryBuilder().configure("hibernate.cfg.xml").build();
            Metadata metadata = new MetadataSources(registry).getMetadataBuilder().build();
            SessionFactory sessionFactory = metadata.getSessionFactoryBuilder().build();

            Session session = sessionFactory.openSession();
                Transaction beginTransaction = session.beginTransaction();
            Transaction transaction = beginTransaction;

            List<Course> courses = session.createQuery("from Course", Course.class).list();
            courses.forEach(course -> {

                List<Student> studentsList = course.getStudents();
                studentsList.forEach(student -> {
                    LinkedPurchaseId linkedPurchaseId = new LinkedPurchaseId(course.getId(), student.getId());
                    LinkedPurchaseList linkedPurchaseList = new LinkedPurchaseList();
                    linkedPurchaseList.setLinkedPurchaseId(linkedPurchaseId);
                    session.persist(linkedPurchaseList);
                });
            });
            transaction.commit();
            sessionFactory.close();
        }
        catch (Exception e){
            System.out.println("Exception:\t" + e.getLocalizedMessage());
        }
    }
}