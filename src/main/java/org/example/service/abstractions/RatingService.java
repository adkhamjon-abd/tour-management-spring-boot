package org.example.service.abstractions;

import org.example.dto.request.CreateRatingRequest;
import org.example.dto.request.UpdateRatingRequest;
import org.example.dto.response.RatingResponse;

import java.util.List;

public interface RatingService {
    RatingResponse createRating(CreateRatingRequest rating);
    List<RatingResponse> getAll();
    Double getAverageRatingByTourId(int id);
    RatingResponse getRatingById(int id);
    void deleteRating(int id);
    RatingResponse updateRating(int id, UpdateRatingRequest updateRating);
    RatingResponse patchRating(int id, UpdateRatingRequest patchRating);

}
