package com.project3.project3.utility;

import org.springframework.web.client.RestTemplate;
import org.springframework.web.util.UriComponentsBuilder;

import java.util.ArrayList;
import java.util.List;

public class FlickrUtil {

    private static final String FLICKR_API_URL = "https://www.flickr.com/services/rest/";
    private static final String FLICKR_API_KEY = System.getenv("FLICKR_API_KEY");
    private static final String FLICKR_METHOD = "flickr.photos.search";
    private static final String RESPONSE_FORMAT = "json";
    private static final String NO_JSON_CALLBACK = "1";
    private static final Integer RADIUS = 1;

    public static List<String> fetchImagesByCoordinates(Double latitude, Double longitude) {
        List<String> imageUrls = new ArrayList<>();

        try {
            String url = UriComponentsBuilder.fromHttpUrl(FLICKR_API_URL)
                    .queryParam("method", FLICKR_METHOD)
                    .queryParam("api_key", FLICKR_API_KEY)
                    .queryParam("lat", latitude)
                    .queryParam("lon", longitude)
                    .queryParam("radius", RADIUS)
                    .queryParam("radius_units", "km")
                    .queryParam("format", RESPONSE_FORMAT)
                    .queryParam("nojsoncallback", NO_JSON_CALLBACK)
                    .queryParam("extras", "url_m")
                    .queryParam("per_page", 10)
                    .queryParam("page", 1)
                    .toUriString();

            RestTemplate restTemplate = new RestTemplate();
            FlickrApiResponse response = restTemplate.getForObject(url, FlickrApiResponse.class);

            if (response != null && response.getPhotos() != null && response.getPhotos().getPhoto() != null) {
                for (FlickrPhoto photo : response.getPhotos().getPhoto()) {
                    if (photo.getUrl_m() != null) {
                        imageUrls.add(photo.getUrl_m());
                    }
                }
            }
        } catch (Exception e) {
            System.err.println("Error fetching images from Flickr: " + e.getMessage());
            e.printStackTrace();
        }
        return imageUrls;
    }

    public static class FlickrApiResponse {
        private Photos photos;
        public Photos getPhotos() {
            return photos;
        }
        public void setPhotos(Photos photos) {
            this.photos = photos;
        }
    }

    public static class Photos {
        private List<FlickrPhoto> photo;
        public List<FlickrPhoto> getPhoto() {
            return photo;
        }
        public void setPhoto(List<FlickrPhoto> photo) {
            this.photo = photo;
        }
    }

    public static class FlickrPhoto {
        private String url_m;
        public String getUrl_m() {
            return url_m;
        }
        public void setUrl_m(String url_m) {
            this.url_m = url_m;
        }
    }
}

