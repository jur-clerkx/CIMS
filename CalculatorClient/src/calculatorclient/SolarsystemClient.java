/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package calculatorclient;

/**
 *
 * @author Jense
 */
public class SolarsystemClient {

    public static void main(String[] args) {
        System.out.println(addSolarsystem("Sol"));
        System.out.println(addSolarsystem("Pizza"));
        System.out.println(addPlanet("Sol", "Zon", 10));
        System.out.println(addPlanet("Sol", "Aarde", 5));
        System.out.println(addPlanet("Sol", "Mars", 9));
        System.out.println(addPlanet("Pizza", "Oven", 20));
        System.out.println(addPlanet("Pizza", "Bodem", 10));
        System.out.println(addPlanet("Pizza", "Tomatensaus", 10));
        System.out.println(addPlanet("Pizza", "Salami", 10));
        System.out.println(addPlanet("Pizza", "Kaas", 10));
        System.out.println(addMoon("Pizza", "Bodem", "Bloem", 5));
        System.out.println(addMoon("Pizza", "Bodem", "Water", 5));
    }

    private static String addMoon(java.lang.String arg0, java.lang.String arg1, java.lang.String arg2, double arg3) {
        webservice.WebSolarsystemService service = new webservice.WebSolarsystemService();
        webservice.WebSolarsystem port = service.getWebSolarsystemPort();
        return port.addMoon(arg0, arg1, arg2, arg3);
    }

    private static String addPlanet(java.lang.String arg0, java.lang.String arg1, double arg2) {
        webservice.WebSolarsystemService service = new webservice.WebSolarsystemService();
        webservice.WebSolarsystem port = service.getWebSolarsystemPort();
        return port.addPlanet(arg0, arg1, arg2);
    }

    private static java.util.List<java.lang.String> getNames() {
        webservice.WebSolarsystemService service = new webservice.WebSolarsystemService();
        webservice.WebSolarsystem port = service.getWebSolarsystemPort();
        return port.getNames();
    }

    private static double getPlanetDiameter(java.lang.String arg0, java.lang.String arg1) {
        webservice.WebSolarsystemService service = new webservice.WebSolarsystemService();
        webservice.WebSolarsystem port = service.getWebSolarsystemPort();
        return port.getPlanetDiameter(arg0, arg1);
    }

    private static boolean solarsystemExists(java.lang.String arg0) {
        webservice.WebSolarsystemService service = new webservice.WebSolarsystemService();
        webservice.WebSolarsystem port = service.getWebSolarsystemPort();
        return port.solarsystemExists(arg0);
    }

    private static String addSolarsystem(java.lang.String arg0) {
        webservice.WebSolarsystemService service = new webservice.WebSolarsystemService();
        webservice.WebSolarsystem port = service.getWebSolarsystemPort();
        return port.addSolarsystem(arg0);
    }
}
