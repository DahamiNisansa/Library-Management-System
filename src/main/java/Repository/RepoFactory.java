package Repository;

import Repository.custom.impl.BookRepoImpl;
import Repository.custom.impl.BorrowRepoImpl;
import Repository.custom.impl.MemberRepoImpl;
import util.RepoType;

public class RepoFactory {
    private static RepoFactory instance;
    private RepoFactory(){}
    public static RepoFactory getInstance() {
        return instance==null?instance=new RepoFactory():instance;
    }

    public <T extends SuperRepo> T getRepoType(RepoType type){
        switch (type){
            case MEMBER: return (T) new MemberRepoImpl();
            case BOOK: return (T) new BookRepoImpl();
            case BORROW: return (T) new BorrowRepoImpl();
        }
        return null;
    }
}
