package com.graduation.utils;

import com.fasterxml.jackson.databind.JsonNode;
import org.testng.annotations.DataProvider;

import java.util.List;

/** TestNG DataProviders backed by testdata.json (no hard-coded values in the tests). */
public final class DataProviders {

    private DataProviders() { }

    @DataProvider(name = "validUser")
    public static Object[][] validUser() {
        return new Object[][]{{TestData.str("/validUser/username"), TestData.str("/validUser/password")}};
    }

    @DataProvider(name = "invalidLogins")
    public static Object[][] invalidLogins() {
        JsonNode arr = TestData.node("/invalidLogins");
        Object[][] data = new Object[arr.size()][2];
        for (int i = 0; i < arr.size(); i++) {
            data[i][0] = arr.get(i).get("username").asText();
            data[i][1] = arr.get(i).get("password").asText();
        }
        return data;
    }

    @DataProvider(name = "knownEmployee")
    public static Object[][] knownEmployee() {
        return new Object[][]{{TestData.str("/knownEmployee")}};
    }

    @DataProvider(name = "invalidEmployee")
    public static Object[][] invalidEmployee() {
        return new Object[][]{{TestData.str("/invalidEmployee")}};
    }

    @DataProvider(name = "newEmployee")
    public static Object[][] newEmployee() {
        return new Object[][]{{TestData.str("/newEmployee/firstName"), TestData.str("/newEmployee/lastName")}};
    }

    @DataProvider(name = "addUserFields")
    public static Object[][] addUserFields() {
        List<String> fields = TestData.list("/addUserFields");
        return new Object[][]{{fields}};
    }

    @DataProvider(name = "sidebarItems")
    public static Object[][] sidebarItems() {
        List<String> items = TestData.list("/sidebarItems");
        return new Object[][]{{items}};
    }
}
