package com.matchasmarketplace.controller;

import com.matchasmarketplace.dao.AboutPhotosDao;
import com.matchasmarketplace.exception.DaoException;
import com.matchasmarketplace.model.AboutPhotos;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;

@RestController
@RequestMapping("/about")
public class AboutPhotosController {

    private AboutPhotosDao aboutPhotosDao;

    public AboutPhotosController(AboutPhotosDao aboutPhotosDao) {
        this.aboutPhotosDao = aboutPhotosDao;
    }

    @RequestMapping(method = RequestMethod.GET)
    public List<AboutPhotos> getAllPhotos() {
        List<AboutPhotos> photos;
        try {
            photos = aboutPhotosDao.getAllPhotos();
        } catch (DaoException e) {
            throw new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR, e.getMessage());
        }
        return photos;
    }
}
