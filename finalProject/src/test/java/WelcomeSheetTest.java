import com.javarush.finalproject.Servlets.WelcomeSheet;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import java.io.IOException;

import static org.mockito.Mockito.*;

class WelcomeSheetTest {

    private WelcomeSheet welcomeSheet;
    private HttpServletRequest request;
    private HttpServletResponse response;
    private HttpSession session;

    @BeforeEach
    void setUp() {
        welcomeSheet = new WelcomeSheet();
        request = mock(HttpServletRequest.class);
        response = mock(HttpServletResponse.class);
        session = mock(HttpSession.class);
    }

    @Test
    void testDoGet_withUserName_redirectsToStartPage() throws IOException, ServletException {
        when(request.getSession(true)).thenReturn(session);
        when(request.getParameter("userName")).thenReturn("TestUser");

        welcomeSheet.doGet(request, response);

        verify(session).setAttribute("userName", "TestUser");
        verify(response).sendRedirect("/start.jsp");
    }

    @Test
    void testDoGet_withGame1_redirectsToMysteriousForest() throws IOException, ServletException {
        when(request.getSession(true)).thenReturn(session);
        when(request.getParameter("game")).thenReturn("1");

        welcomeSheet.doGet(request, response);

        verify(session).setAttribute(eq("stage"), anyString());
        verify(response).sendRedirect("/mysteriousForest.jsp");
    }


}