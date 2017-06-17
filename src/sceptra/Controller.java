package sceptra;

import brockers.Brocker;
import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
import javafx.beans.property.SimpleDoubleProperty;
import javafx.beans.property.SimpleFloatProperty;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.VBox;

import java.util.ArrayList;
import java.util.Iterator;

public class Controller extends VBox {

    @FXML
    CheckBox history;
    @FXML
    CheckBox filter;
    @FXML
    TextField topval;
    @FXML
    ComboBox<String> category;

    @FXML
    GridPane grid;
//    @FXML
//    ListView<KeywordModel> listView;
    @FXML
    private TextArea userText;
    @FXML
    private Button findBut;

    public void initialize() {

        category.getItems().addAll("keywords",
                "requirements", "packages", "users");
        category.getSelectionModel().selectFirst();

        // initialization here, if needed...
    }


    @FXML
    protected void doSomething() {

        String url = null;

        if (category.getSelectionModel().getSelectedItem().equalsIgnoreCase("requirements") &&
                history.isSelected()) {
            url = Routes.REQUIREMENT_BY_HISTORY;
            JsonArray response1 = new Brocker()
                    .postRequestArrayRes(url, userText.getText());
            addDataAsJsonArr(response1);

        } else if (category.getSelectionModel().getSelectedItem().equalsIgnoreCase("requirements") ||
                (category.getSelectionModel().getSelectedItem().equalsIgnoreCase("keywords"))) {
            url = Routes.REQUIREMENT;
            JsonObject response;
            int precentage = 0;
            if (filter.isSelected() && topval.getText() != null) {
                try {
                    int val = Integer.parseInt(topval.getText());

                    if (val >= 0 && val <= 100) {
                        precentage = val;
                    }
                    response = new Brocker()
                            .postRequest(url + "/" + precentage, userText.getText());
                    addData(response);

                } catch (Exception e) {

                }
            } else {
                response = new Brocker()
                        .postRequest(url, userText.getText());
                addData(response);
            }


        } else if (category.getSelectionModel().getSelectedItem().equalsIgnoreCase("packages")) {
            url = Routes.REQUIREMENT_GET_PACKAGES;
            int precentage = 0;
            JsonArray response;
            if (filter.isSelected() && topval.getText() != null) {
                try {
                    int val = Integer.parseInt(topval.getText());

                    if (val >= 0 && val <= 100) {
                        precentage = val;
                    }
                    response = new Brocker()
                            .postRequestArrayRes(url + "/" + precentage, userText.getText());
                    addSimpleJsonArray(response);

                } catch (Exception e) {


                }

            } else {

                response = new Brocker()
                        .postRequestArrayRes(url, userText.getText());
                addSimpleJsonArray(response);
            }


        } else if (category.getSelectionModel().getSelectedItem().equalsIgnoreCase("users")) {
            url = Routes.REQUIREMENT_GET_USERS;
            int precentage = 0;
            JsonObject response;
            if (filter.isSelected() && topval.getText() != null) {
                try {
                    int val = Integer.parseInt(topval.getText());

                    if (val >= 0 && val <= 100) {
                        precentage = val;
                    }
                    response = new Brocker()
                            .postRequest(url + "/" + precentage, userText.getText());
                    addUserData(response);

                } catch (Exception e) {


                }

            } else {

                response = new Brocker()
                        .postRequest(url, userText.getText());
                addUserData(response);
            }

        }


      /*  JsonObject response;
        int percentage = 0;
        if (filter.isSelected() && topval.getText() != null) {
            try {
                int val = Integer.parseInt(topval.getText());

                if (val >= 0 && val <= 100) {
                    percentage = val;
                }
                response = new Brocker()
                        .postRequest(url + "/" + percentage, userText.getText());
                addData(response);

            } catch (Exception e) {

            }
        } else {
            if (history.isSelected()) {
                JsonArray response1 = new Brocker()
                        .postRequestArrayRes(url, userText.getText());
                addDataAsJsonArr(response1);

            } else {
                response = new Brocker()
                        .postRequest(url, userText.getText());
                addData(response);

            }

        }*/

    }


    protected void addData(JsonObject jsonObject) {
        ArrayList<KeywordModel> models = new ArrayList<>();
        TableColumn tableColumn = new TableColumn();
        TableColumn tableColumn1 = new TableColumn();
        ListView<KeywordModel> listView = new ListView<>();
        TableView<KeywordModel> table = new TableView<>();

        tableColumn.setText("keyword");
        tableColumn1.setText("Score");
        tableColumn.setCellValueFactory(new PropertyValueFactory<RequirementModel, String>("key"));
        tableColumn1.setCellValueFactory(new PropertyValueFactory<RequirementModel, String>("value"));

        System.out.println(jsonObject.toString());
        Iterator keys = jsonObject.entrySet().iterator();

        while (keys.hasNext()) {
            if (keys.next().toString().contains("=")) {
                String key = keys.next().toString().split("=")[0];
                String value = keys.next().toString().split("=")[1];
                models.add(new KeywordModel(key, value));
                ListCell cell = new ListCell();
                cell.setText(key);
                ListCell cell1 = new ListCell();
                cell1.setText(value);
            }

        }
        final ObservableList<KeywordModel> data =
                FXCollections.observableArrayList(models);
        table.setItems(data);
        table.getColumns().addAll(tableColumn, tableColumn1);
        grid.add(table, 0, 3);

    }

    protected void addDataAsJsonArr(JsonArray jsonArray) {
        ArrayList<RequirementModel> models = new ArrayList<>();
        TableColumn tableColumn = new TableColumn();
        TableColumn tableColumn1 = new TableColumn();
        TableColumn tableColumn2 = new TableColumn();
        TableColumn tableColumn3 = new TableColumn();
        TableColumn tableColumn4 = new TableColumn();

        tableColumn.setCellValueFactory(new PropertyValueFactory<RequirementModel, String>("id"));
        tableColumn1.setCellValueFactory(new PropertyValueFactory<RequirementModel, String>("requirement"));
        tableColumn2.setCellValueFactory(new PropertyValueFactory<RequirementModel, String>("requirementStems"));
        tableColumn3.setCellValueFactory(new PropertyValueFactory<RequirementModel, String>("keyword"));
        tableColumn4.setCellValueFactory(new PropertyValueFactory<RequirementModel, String>("keywordScore"));

        tableColumn.setText("id");
        tableColumn1.setText("requirement");
        tableColumn1.setMaxWidth(300);
        tableColumn2.setText("requirementStems");
        tableColumn1.setMaxWidth(300);

        tableColumn3.setText("keyword");
        tableColumn4.setText("keywordScore");
//        ListView<RequirementModel> listView = new ListView<>();
        TableView<RequirementModel> table = new TableView<RequirementModel>();

        System.out.println(jsonArray.toString());
        for (int a = 0; a < jsonArray.size(); a++) {

            JsonObject jsonObject = jsonArray.get(a).getAsJsonObject();

            Integer id = jsonObject.get("id").getAsInt();
            String requirement = jsonObject.get("requirement").getAsString();
            String requirementStems = jsonObject.get("requirementStems").getAsString();
            String keyword = jsonObject.get("keyword").getAsString();
            Float keywordScore = jsonObject.get("keywordScore").getAsFloat();
            Integer acceptance = null;
            if (jsonObject.has("acceptance") && jsonObject.get("acceptance").isJsonNull()) {
                acceptance = 0;
            } else if (jsonObject.has("acceptance") && !jsonObject.get("acceptance").isJsonNull()) {
                acceptance = jsonObject.get("acceptance").getAsInt();
            }


            RequirementModel requirementModel = new RequirementModel(id,
                    requirement, requirementStems, keyword, keywordScore, acceptance);
            models.add(requirementModel);
        }
        final ObservableList<RequirementModel> data =
                FXCollections.observableArrayList(models);
        table.setItems(data);

        table.getColumns().addAll(tableColumn, tableColumn1, tableColumn2, tableColumn3, tableColumn4);
////
//        this.dataTable.setItems(data);

        grid.add(table, 0, 3);

    }

    protected void addSimpleJsonArray(JsonArray jsonArray) {
        ArrayList<PackageModel> models = new ArrayList<>();
        TableView<PackageModel> table = new TableView<>();
        TableColumn tableColumn = new TableColumn();
        tableColumn.setCellValueFactory(new PropertyValueFactory<PackageModel, String>("packageName"));

        for (int a = 0; a < jsonArray.size(); a++) {

            String string = jsonArray.get(a).getAsString();
            PackageModel requirementModel = new PackageModel(string);
            models.add(requirementModel);

        }
        final ObservableList<PackageModel> data =
                FXCollections.observableArrayList(models);
        table.setItems(data);
        table.getColumns().addAll(tableColumn);
        grid.add(table, 0, 3);

    }

    protected void addUserData(JsonObject jsonObject) {
        ArrayList<UserModel> models = new ArrayList<>();
        TableColumn tableColumn = new TableColumn();
        TableColumn tableColumn1 = new TableColumn();
        TableColumn tableColumn2 = new TableColumn();
        TableColumn tableColumn3 = new TableColumn();

        tableColumn.setCellValueFactory(new PropertyValueFactory<UserModel, String>("username"));
        tableColumn1.setCellValueFactory(new PropertyValueFactory<UserModel, String>("technologyName"));
        tableColumn2.setCellValueFactory(new PropertyValueFactory<UserModel, Double>("percentage"));
        tableColumn3.setCellValueFactory(new PropertyValueFactory<UserModel, Double>("overallQuality"));

        tableColumn.setText("User Name");
        tableColumn1.setText("Technology Name");
        tableColumn1.setMaxWidth(300);
        tableColumn2.setText("percentage");
        tableColumn1.setMaxWidth(300);
        tableColumn3.setText("Overall quality");
//        ListView<RequirementModel> listView = new ListView<>();
        TableView<UserModel> table = new TableView<UserModel>();
        Iterator keys = jsonObject.entrySet().iterator();

        System.out.println(jsonObject.toString());
        while (keys.hasNext()) {
            if (keys.next().toString().contains("=")) {
                String user = keys.next().toString().split("=")[0];
                JsonArray techlist = jsonObject.getAsJsonArray(user);
                for (int a = 0; a < techlist.size(); a++) {

                    JsonObject techObj = techlist.get(a).getAsJsonObject();
                    String technologyName = techObj.get("technologyName").getAsString();
                    Double percentage = techObj.get("percentage").getAsDouble();
                    Double overallQuality = techObj.get("overallQuality").getAsDouble();

                    UserModel userModel = new UserModel(user, technologyName, percentage, overallQuality);
                    models.add(userModel);
                }

            }

        }

        final ObservableList<UserModel> data =
                FXCollections.observableArrayList(models);
        table.setItems(data);

        table.getColumns().addAll(tableColumn, tableColumn1, tableColumn2, tableColumn3);

        grid.add(table, 0, 3);

    }

    public static class UserModel {

        private final SimpleStringProperty username;

        private final SimpleStringProperty technologyName;

        private final SimpleDoubleProperty percentage;
        private final SimpleDoubleProperty overallQuality;

        public UserModel(String userName,
                         String technologyName,
                         Double precentage,
                         Double overollQuality) {
            this.username = new SimpleStringProperty(userName);
            this.technologyName = new SimpleStringProperty(technologyName);
            this.percentage = new SimpleDoubleProperty(precentage);
            this.overallQuality = new SimpleDoubleProperty(overollQuality);
        }

        public String getUsername() {
            return username.get();
        }

        public void setUsername(String username) {
            this.username.set(username);
        }

        public SimpleStringProperty usernameProperty() {
            return username;
        }

        public String getTechnologyName() {
            return technologyName.get();
        }

        public void setTechnologyName(String technologyName) {
            this.technologyName.set(technologyName);
        }

        public SimpleStringProperty technologyNameProperty() {
            return technologyName;
        }

        public double getPercentage() {
            return percentage.get();
        }

        public void setPercentage(double percentage) {
            this.percentage.set(percentage);
        }

        public SimpleDoubleProperty percentageProperty() {
            return percentage;
        }

        public double getOverallQuality() {
            return overallQuality.get();
        }

        public void setOverallQuality(double overallQuality) {
            this.overallQuality.set(overallQuality);
        }

        public SimpleDoubleProperty overallQualityProperty() {
            return overallQuality;
        }

        @Override
        public String toString() {
            return "UserModel{" +

                    "username=" + username +
                    ", technologyName=" + technologyName +
                    ", percentage=" + percentage +
                    ", overallQuality=" + overallQuality +
                    '}';
        }


    }

    public static class PackageModel {

        private final SimpleStringProperty packageName;

        public PackageModel(String packageName) {

            this.packageName = new SimpleStringProperty(packageName);
        }

        public String getPackageName() {

            return packageName.get();
        }

        public void setPackageName(String packageName) {
            this.packageName.set(packageName);
        }

        public SimpleStringProperty packageNameProperty() {
            return packageName;
        }

        @Override
        public String toString() {
            return "PackageModel{" +
                    "packageName=" + packageName +
                    '}';
        }
    }

    public static class KeywordModel {

        private final SimpleStringProperty key;
        private final SimpleStringProperty value;

        public KeywordModel(String key, String value) {
            this.key = new SimpleStringProperty(key);
            this.value = new SimpleStringProperty(value);
        }

        public String getKey() {
            return key.get();
        }

        public void setKey(String key) {
            this.key.set(key);
        }

        public String getValue() {
            return value.get();
        }

        public void setValue(String value) {
            this.value.set(value);
        }

        @Override
        public String toString() {
            return "KeywordModel{" +

                    "key='" + key + '\'' +
                    ", value='" + value + '\'' +
                    '}';
        }

    }

    public static class RequirementModel {

        private final SimpleIntegerProperty id;
        private final SimpleStringProperty requirement;
        private final SimpleStringProperty requirementStems;
        private final SimpleStringProperty keyword;
        private final SimpleFloatProperty keywordScore;
        private final SimpleIntegerProperty acceptance;

        public RequirementModel(Integer id,
                                String requirement,
                                String requirementStems,
                                String keyword,
                                Float keywordScore,
                                Integer acceptance) {
            this.id = new SimpleIntegerProperty(id);
            this.requirement = new SimpleStringProperty(requirement);
            this.requirementStems = new SimpleStringProperty(requirementStems);
            this.keyword = new SimpleStringProperty(keyword);
            this.keywordScore = new SimpleFloatProperty(keywordScore);
            this.acceptance = new SimpleIntegerProperty(acceptance);
        }

        public int getId() {
            return id.get();
        }

        public void setId(int id) {
            this.id.set(id);
        }

        public SimpleIntegerProperty idProperty() {
            return id;
        }

        public String getRequirement() {
            return requirement.get();
        }

        public void setRequirement(String requirement) {
            this.requirement.set(requirement);
        }

        public SimpleStringProperty requirementProperty() {
            return requirement;
        }

        public String getRequirementStems() {
            return requirementStems.get();
        }

        public void setRequirementStems(String requirementStems) {
            this.requirementStems.set(requirementStems);
        }

        public SimpleStringProperty requirementStemsProperty() {
            return requirementStems;
        }

        public String getKeyword() {
            return keyword.get();
        }

        public void setKeyword(String keyword) {
            this.keyword.set(keyword);
        }

        public SimpleStringProperty keywordProperty() {
            return keyword;
        }

        public Float getKeywordScore() {
            return keywordScore.get();
        }

        public void setKeywordScore(Float keywordScore) {
            this.keywordScore.set(keywordScore);
        }

        @Override
        public String toString() {
            return "RequirementModel{" +
                    "id=" + id +
                    ", requirement=" + requirement +
                    ", requirementStems=" + requirementStems +
                    ", keyword=" + keyword +
                    ", keywordScore=" + keywordScore +
                    ", acceptance=" + acceptance +
                    '}';
        }

        public SimpleFloatProperty keywordScoreProperty() {
            return keywordScore;
        }

        public Integer getAcceptance() {
            return acceptance.get();
        }

        public void setAcceptance(Integer acceptance) {
            this.acceptance.set(acceptance);
        }

        public SimpleIntegerProperty acceptanceProperty() {
            return acceptance;
        }
    }

}
