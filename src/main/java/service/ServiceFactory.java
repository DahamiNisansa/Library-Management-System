package service;

import service.custom.impl.BookServiceImpl;
import service.custom.impl.BorrowServiceImpl;
import service.custom.impl.MemberServiceImpl;
import util.ServiceType;

public class ServiceFactory {
    private static ServiceFactory instance;
    private void BoFactory(){}
    public static ServiceFactory getInstance() {
        return instance==null?instance=new ServiceFactory():instance;
    }
    public <T extends SuperService> T getBoType(ServiceType type){

        switch (type){
            case MEMBER: return (T) new MemberServiceImpl();
            case BOOK: return (T) new BookServiceImpl();
            case BORROW: return (T) new BorrowServiceImpl();
        }
        return null;

    }
}
