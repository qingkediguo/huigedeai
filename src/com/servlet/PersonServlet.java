package com.servlet;

import com.domain.Gift;
import com.domain.Person;
import com.service.Serviceimpl;
import net.sf.json.JSONArray;
import net.sf.json.JSONObject;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.List;
import java.util.Random;


@WebServlet("/PersonServlet")
public class PersonServlet extends HttpServlet {
    private static final long serialVersionUID = 1L;

    private static int index = 0; // 暂存圈数
    private static Gift[] gifts = new Gift[8];

    static {
        gifts[0] = new Gift("标配4", 4);
        gifts[1] = new Gift("4+4+4", 12);
        gifts[2] = new Gift("标配4", 4);
        gifts[3] = new Gift("4x4", 16);
        gifts[4] = new Gift("标配4", 4);
        gifts[5] = new Gift("4+4", 8);
        gifts[6] = new Gift("标配4", 4);
        gifts[7] = new Gift("标配4", 4);
    }

    public PersonServlet() {
        super();
    }

    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        request.setCharacterEncoding("utf-8");
        response.setContentType("application/json;charset=utf-8");
        //response.setContentType("text/html;charset=utf-8");

        Serviceimpl se = new Serviceimpl();
        String thisPar = request.getParameter("action");
        PrintWriter pw = response.getWriter();

        if (thisPar.equals("what")) { // 只传回圈数
            index = new Random().nextInt(8);

            JSONObject whatJson = new JSONObject();

            JSONObject W = new JSONObject();

            W.put("count", gifts[index].getCount());
            W.put("name", gifts[index].getName());

            whatJson.put("what", W);

            pw.write(whatJson.toString());
        } else if (thisPar.equals("all")) { // 获得所有的json
            List<Person> allBean = null;
            try {
                allBean = se.getAllBean();
            } catch (Exception e) {
                e.printStackTrace();
            }

            JSONObject allJson1 = new JSONObject();

            JSONArray allPersonJson = new JSONArray();
            for (Person p : allBean) {
                JSONObject P = new JSONObject();

                P.put("id", p.getId());
                P.put("name", p.getName());
                P.put("weight", p.getWeight());
                P.put("times", p.getTimes());
                P.put("count", p.getCount());

                allPersonJson.add(P);
            }

            JSONArray allGiftJson = new JSONArray();
            for (Gift gf : gifts) {
                JSONObject G = new JSONObject();

                G.put("count", gf.getCount());
                G.put("name", gf.getName());

                allGiftJson.add(G);
            }

            allJson1.put("users", allPersonJson);
            allJson1.put("gifts", allGiftJson);

            pw.write(allJson1.toString());

        } else if (thisPar.equals("who")) { // 只传回人物
            if (index == 0) {
                index = 4;
            }
            Person p1;
            try {
                p1 = se.search(gifts[index].count);
                JSONObject whoJson = new JSONObject();
//                JSONObject whoJson1 = new JSONObject();
//                whoJson = JSONObject.fromObject(p1);
                whoJson.put("who", JSONObject.fromObject(p1));
                pw.write(whoJson.toString());
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }
}
