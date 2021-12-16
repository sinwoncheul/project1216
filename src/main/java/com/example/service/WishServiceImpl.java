package com.example.service;

import java.util.List;

import com.example.dto.WishList;
import com.example.repository.WishRepository;

import org.apache.ibatis.session.SqlSessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class WishServiceImpl implements WishService {

    @Autowired
    WishRepository wRepository;

    @Autowired
    SqlSessionFactory sqlSessionFactory;

    @Override
    public List<WishList> wishListselect(String id) {
        try {
            return sqlSessionFactory.openSession().selectList("WishList.wishListselect", id);
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    @Override
    public int wishListupdate(WishList wList) {
        // TODO Auto-generated method stub
        return 0;
    }

    @Override
    public int wishListdelete(Long id) {
        try {
            return sqlSessionFactory.openSession().delete("WishList.wishListdelete", id);
        } catch (Exception e) {
            e.printStackTrace();
            return 0;
        }
    }

    @Override
    public int wishcountselect(String id) {
        try {
            return sqlSessionFactory.openSession().selectOne("WishList.wishcountselect");
        } catch (Exception e) {
            e.printStackTrace();
            return 0;
        }
    }

    @Override
    public int selectCartkey(Long id) {
        try {
            return sqlSessionFactory.openSession().selectOne("WishList.selectCartkey", id);
        } catch (Exception e) {
            e.printStackTrace();
            return 0;
        }
    }

    @Override
    public List<WishList> companycarthit() {
        try {
            return sqlSessionFactory.openSession().selectList("WishList.companycarthit");
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }
}
