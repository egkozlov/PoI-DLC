package servlet;


import net.sf.sprockets.google.Place;
import net.sf.sprockets.google.Places;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.List;
import java.util.logging.Logger;

public class PlacesServlet extends HttpServlet {
    private static Logger logger= Logger.getLogger("PlacesServlet.class");
    private static String KEY = "AIzaSyDaBRZG4y4SxikTqxl6LfPtvRiM2_plBdA";
    public PlacesServlet() {

    }

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {

        logger.warning("query string " + req.getServletPath());

        List<Place> placeList = Places.nearbySearch(new Places.Params().location(47.60567, -122.3315).radius(50000)
                        .types("bank","airport").openNow(),
                Places.Field.NAME, Places.Field.VICINITY, Places.Field.INTL_PHONE_NUMBER).getResult();
        JSONArray result = new JSONArray();
        for(Place place :placeList){
            JSONObject formDetailsJson = new JSONObject();
            formDetailsJson.put("name", place.getName());
            formDetailsJson.put("vicinity", place.getVicinity());
            result.add(formDetailsJson);
        }
        if (req.getServletPath().equals("/places")) {
            resp.setContentType("application/json");
            PrintWriter out = resp.getWriter();
            out.print(result);
            out.flush();
        }
    }

}
