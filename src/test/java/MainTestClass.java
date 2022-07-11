import ua.dao.CustomerMySqlDao;
import ua.dao.DataSource;
import ua.domain.Customer;
import ua.dto.CustomerDto;
import ua.services.UserService;
import ua.services.UserServiceImpl;

import java.util.List;

public class MainTestClass {
    public static void main(String[] args) {
        dbInit();

        UserService userService = new UserServiceImpl();
        CustomerMySqlDao customerMySqlDao = new CustomerMySqlDao();

        List<CustomerDto> list = userService.getAllCustomer();
//        List<Customer> list2 = customerMySqlDao.getAll();
        for(CustomerDto c : list){
            System.out.println(c);
        }
    }

    static void dbInit() {
        DataSource.inntConfig("com.mysql.cj.jdbc.Driver", "jdbc:mysql://localhost:3306/periodicals", "root", "root");
    }

}
