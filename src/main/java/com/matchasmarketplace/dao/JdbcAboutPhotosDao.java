package com.matchasmarketplace.dao;

import com.matchasmarketplace.exception.DaoException;
import com.matchasmarketplace.model.AboutPhotos;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.CannotGetJdbcConnectionException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.support.rowset.SqlRowSet;
import org.springframework.stereotype.Component;

import javax.sql.DataSource;
import java.util.ArrayList;
import java.util.List;
@Component
public class JdbcAboutPhotosDao implements AboutPhotosDao {

    private final JdbcTemplate jdbcTemplate;

    @Autowired
    public JdbcAboutPhotosDao(DataSource dataSource) {
        this.jdbcTemplate = new JdbcTemplate(dataSource);
    }

    @Override
    public List<AboutPhotos> getAllPhotos() {
        List<AboutPhotos> photos = new ArrayList<>();
        String sql = "SELECT * FROM about_photos;";

        try {
            SqlRowSet result = jdbcTemplate.queryForRowSet(sql);
            while (result.next()) {
                photos.add(mapRowToPhotos(result));
            }
        } catch (CannotGetJdbcConnectionException e) {
            throw new DaoException("Unable to connect to the server", e);
        }
        return photos;
    }

    private AboutPhotos mapRowToPhotos(SqlRowSet rowSet) {
        AboutPhotos photos  = new AboutPhotos();
        photos.setAboutPhotosId(rowSet.getInt("about_photos_id"));
        photos.setName(rowSet.getString("name"));
        photos.setImgUrl(rowSet.getString("img_url"));
        return photos;
    }
}
