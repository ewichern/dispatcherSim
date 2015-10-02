package odu.edu.cs.ewichern.dispatcher;

import java.util.ArrayList;
import java.util.SortedMap;
import java.util.TreeMap;

import javafx.application.Application;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.stage.Stage;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ListView;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.VBox;
import javafx.scene.text.Font;

public class DispatcherGUI extends Application {

	GridPane grid = new GridPane();
	private static int PRIORITY_LEVELS = 3;
	private static int NUM_FILL_PROCESSESS = 3;
	private static Dispatcher dispatcher = new Dispatcher(PRIORITY_LEVELS);
	private static int maxPriority = dispatcher.getMaxPriority();
	private static Object[] queueArray;
	private static SortedMap<Integer, ListView<String>> queueViews = new TreeMap<Integer, ListView<String>>();
	private static SortedMap<Integer, ObservableList<String>> queueLists = new TreeMap<Integer, ObservableList<String>>();
	private static SortedMap<Integer, Label> queueLabels = new TreeMap<Integer, Label>();
	private static SortedMap<Integer, VBox> queueBoxes = new TreeMap<Integer, VBox>();

	private static Process selectedProcess;
	private static ListView<String> selectedProcessView = new ListView<String>();
	private static ObservableList<String> selectedProcessText = FXCollections.observableArrayList();
	final Label selectedProcessLabel = new Label();
	private static VBox selectedProcessBox = new VBox();

	private static ListView<String> blockedView = new ListView<String>();
	private static ObservableList<String> blockedList = FXCollections.observableArrayList();
	final Label blockedLabel = new Label();
	private static VBox blockedBox = new VBox();

	private ChangeListener<String> selectionListener = new ChangeListener<String>() {
		public void changed(ObservableValue<? extends String> ov, String old_val, String new_val) {
			System.err.println(old_val + " " + new_val);
			if (new_val != null) {
				new_val.trim();
				String PIDstring = new_val.substring(0, new_val.indexOf(" "));
				int PID = Integer.parseInt(PIDstring);
				selectedProcess = dispatcher.getByPID(PID);
				selectedProcessText.setAll(selectedProcess.stringArray());
				selectedProcessView.setItems(selectedProcessText);
			}
		}
	};

	public void init() {
		for (int i = 0; i <= maxPriority; i++) {
			System.out.println(i);
			for (int j = 0; j < NUM_FILL_PROCESSESS; j++) {
				dispatcher.createProcess(i);
			}
			queueArray = dispatcher.queueArray();

			ListView<String> view = new ListView<String>();
			ObservableList<String> list = FXCollections.observableArrayList();
			Label label = new Label();
			VBox box = new VBox();

			box.setPrefSize(175, 300);
			label.setFont(Font.font("Arial", 14));
			label.setText("PID - name (priority = 0)");

			list.addAll(((ProcessQueue) queueArray[i]).stringArray());
			view.setItems(list);
			view.getSelectionModel().selectedItemProperty().addListener(selectionListener);

			box.getChildren().addAll(label, view);

			queueViews.put(i, view);
			queueLists.put(i, list);
			queueLabels.put(i, label);
			queueBoxes.put(i, box);
			grid.add(box, i, 1);
		}
		selectedProcessLabel.setAlignment(Pos.CENTER);
		selectedProcessLabel.setFont(Font.font("Arial", 14));
		selectedProcessLabel.setText("Selected Process");
		selectedProcessBox.setPrefSize(175, 175);

		blockedLabel.setAlignment(Pos.CENTER);
		blockedLabel.setFont(Font.font("Arial", 14));
		blockedLabel.setText("Blocked Processes");
		blockedBox.setPrefSize(175, 300);
		blockedBox.getChildren().addAll(blockedLabel, blockedView);
		blockedView.getSelectionModel().selectedItemProperty().addListener(selectionListener);
		grid.add(blockedBox, (maxPriority + 1), 1);
	}

	private void refresh() {
		queueArray = dispatcher.queueArray();

		for (int i = 0; i <= maxPriority; i++) {
			ListView<String> view = queueViews.get(i);
			ObservableList<String> list = queueLists.get(i);
			Label label = queueLabels.get(i);
			VBox box = queueBoxes.get(i);

			list.setAll(((ProcessQueue) queueArray[i]).stringArray());
			view.setItems(list);
			box.getChildren().setAll(label, view);
		}
	}

	@Override
	public void start(Stage stage) {
		stage.setTitle("Dispatcher Simulation");
		grid.setAlignment(Pos.CENTER);
		grid.setHgap(10);
		grid.setVgap(10);
		grid.setPadding(new Insets(25, 25, 25, 25));
		grid.setGridLinesVisible(false);

		Button terminateButton = new Button("Terminate");
		terminateButton.setOnAction(new EventHandler<ActionEvent>() {
			public void handle(ActionEvent event) {
				dispatcher.killProcess(selectedProcess);
				selectedProcessView.setItems(null);
				refresh();
			}
		});

		selectedProcessBox.getChildren().addAll(selectedProcessLabel, selectedProcessView, terminateButton);
		grid.add(selectedProcessBox, 3, 0);

		Scene scene = new Scene(grid, 800, 600);

		stage.setScene(scene);
		stage.show();
	}

	public static void main(String[] args) {
		launch(args);
	}
}
