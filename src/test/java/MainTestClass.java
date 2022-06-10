import ua.domain.Publisher;
import ua.services.PublisherServiceImpl;

import java.util.List;

public class MainTestClass {
    public static void main(String[] args) {
        PublisherServiceImpl service = new PublisherServiceImpl();
        List<Publisher> publisherList = service.getAll();
        int p = 0;
        publisherList.iterator().hasNext();
        for (Publisher publisher : publisherList) {
            System.out.println();
            for (int i = 1; i <= 4; i++) {
                System.out.print(i + ". " + publisher.getName() + " ");
            }
        }
    }
}
