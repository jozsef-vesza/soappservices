/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package hu.jozsef.vesza.so.servlets;

import com.google.appengine.api.datastore.Blob;
import com.googlecode.objectify.Objectify;
import hu.jozsef.vesza.so.model.Event;
import hu.jozsef.vesza.so.utils.OfyService;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.util.logging.Logger;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.apache.commons.io.IOUtils;

/**
 *
 * @author József
 */
public class EventImageService extends HttpServlet
{

    private static final Logger log = Logger.getLogger(EventManagerServlet.class.getName());
    Objectify objectify = OfyService.ofy();

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException
    {
        Long eventId = new Long(request.getParameter("identifier"));
        Event fetchedEvent = objectify.load().type(Event.class).id(eventId).now();
        String imageUrl = "/WEB-INF/" + fetchedEvent.getLocation().getShortName() + ".png";
        String imageRealPath = this.getServletContext().getRealPath(imageUrl);
        InputStream imgStream = new FileInputStream(new File(imageRealPath));

        Blob image = new Blob(IOUtils.toByteArray(imgStream));
        if (image != null)
        {
            response.setContentType("image/jpeg");
            response.getOutputStream().write(image.getBytes());
        }
        else
        {
            response.getWriter().write("no image");
        }

    }

}
