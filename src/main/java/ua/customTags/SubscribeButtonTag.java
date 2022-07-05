package ua.customTags;

import ua.services.UserService;
import ua.services.UserServiceImpl;

import javax.servlet.jsp.JspException;
import javax.servlet.jsp.JspWriter;
import javax.servlet.jsp.tagext.TagSupport;

public class SubscribeButtonTag extends TagSupport {
    private Object customerEmail;
    private Object publisherId;

    private UserService userService = new UserServiceImpl();
    @Override
    public int doStartTag() throws JspException {
        String email = (String) customerEmail;
        int customerId = userService.getCustomer(email).getId();
        int pubId = Integer.valueOf((String)publisherId);

        if (!userService.isSubscribed(customerId, pubId)){
            return EVAL_BODY_INCLUDE;
        } else {
            return SKIP_BODY;
        }
    }

    public Object getCustomerEmail() {
        return customerEmail;
    }

    public void setCustomerEmail(Object customerEmail) {
        this.customerEmail = customerEmail;
    }

    public Object getPublisherId() {
        return publisherId;
    }

    public void setPublisherId(Object publisherId) {
        this.publisherId = publisherId;
    }
}
