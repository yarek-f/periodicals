package ua.customTags;

import ua.services.UserService;
import ua.services.UserServiceImpl;

import javax.servlet.jsp.JspException;
import javax.servlet.jsp.JspWriter;
import javax.servlet.jsp.tagext.DynamicAttributes;
import javax.servlet.jsp.tagext.TagSupport;
import java.io.IOException;
import java.time.LocalDateTime;

public class UnsubscribeButtonTag extends TagSupport {
    private Object customerEmail;
    private Object publisherId;

    private UserService userService = new UserServiceImpl();

//    @Override
//    public void setDynamicAttribute(String s, String s1, Object o) throws JspException {
//
//    }

    @Override
    public int doStartTag() throws JspException {
        JspWriter out = pageContext.getOut();
        String email = (String) customerEmail;
        int customerId = userService.getCustomer(email).getId();
        int pubId = Integer.valueOf((String)publisherId);

        if (userService.isSubscribed(customerId, pubId)){
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
