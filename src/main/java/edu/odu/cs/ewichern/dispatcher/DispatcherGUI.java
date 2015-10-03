package edu.odu.cs.ewichern.dispatcher;

import java.util.SortedMap;
import java.util.TreeMap;

import javafx.application.Application;
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
import javafx.scene.control.TextField;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.text.Font;
import javafx.scene.text.Text;

public class DispatcherGUI extends Application {

	GridPane grid = new GridPane();
	private static int PRIORITY_LEVELS = 3;
	private static int NUM_FILL_PROCESSESS = 2;
	private static Dispatcher dispatcher = new Dispatcher(PRIORITY_LEVELS);
	private static int maxPriority = dispatcher.getMaxPriority();
	Object[] queueArray;
	SortedMap<Integer, ListView<String>> queueViews = new TreeMap<Integer, ListView<String>>();
	SortedMap<Integer, ObservableList<String>> queueLists = new TreeMap<Integer, ObservableList<String>>();
	SortedMap<Integer, Label> queueLabels1 = new TreeMap<Integer, Label>();
	SortedMap<Integer, Label> queueLabels2 = new TreeMap<Integer, Label>();
	SortedMap<Integer, VBox> queueBoxes = new TreeMap<Integer, VBox>();

	Process selectedProcess;
	ListView<String> selectedProcessView = new ListView<String>();
	ObservableList<String> selectedProcessList = FXCollections.observableArrayList();
	Label selectedProcessLabel = new Label();
	VBox selectedProcessBox = new VBox();

	Process runningProcess;
	ListView<String> runningProcessView = new ListView<String>();
	ObservableList<String> runningProcessList = FXCollections.observableArrayList();
	Label runningProcessLabel = new Label();
	VBox runningProcessBox = new VBox();

	ListView<String> blockedView = new ListView<String>();
	ObservableList<String> blockedList = FXCollections.observableArrayList();
	Label blockedLabel = new Label();
	VBox blockedBox = new VBox();

//	private ChangeListener<String> selectionListener = new ChangeListener<String>() {
//		public void changed(ObservableValue<? extends String> ov, String old_val, String new_val) {
//			System.err.println(old_val + " " + new_val);
//			if (new_val != null) {
//				new_val.trim();
//				String PIDstring = new_val.substring(0, new_val.indexOf(" "));
//				int PID = Integer.parseInt(PIDstring);
//				selectedProcess = dispatcher.getByPID(PID);
//				selectedProcessList.setAll(selectedProcess.stringArray());
//				selectedProcessView.setItems(selectedProcessList);
//			}
//		}
//	};

	private EventHandler<MouseEvent> clickHandler = new EventHandler<MouseEvent>() {
		public void handle(MouseEvent event) {
			ListView<String> s = (ListView<String>) event.getSource();
			String sourceValue = s.getSelectionModel().getSelectedItem();
			if (sourceValue != null) {
				String PIDstring = sourceValue.substring(0, sourceValue.indexOf(" "));
				int PID = Integer.parseInt(PIDstring);
				selectedProcess = dispatcher.getByPID(PID);
				selectedProcessList.setAll(selectedProcess.stringArray());
				selectedProcessView.setItems(selectedProcessList);
			}
		}
	};

	public void init() {
		for (int i = 0; i <= maxPriority; i++) {
			System.out.println(i);
//			for (int j = 0; j < NUM_FILL_PROCESSESS; j++) {
//				dispatcher.createProcess(i);
//			}
			queueArray = dispatcher.queueArray();

			ListView<String> view = new ListView<String>();
			ObservableList<String> list = FXCollections.observableArrayList();
			Label label1 = new Label();
			Label label2 = new Label();
			VBox box = new VBox();

			box.setPrefSize(175, 300);
			label1.setFont(Font.font("Arial", 14));
			label2.setFont(Font.font("Arial", 14));
			label1.setText("Priority = " + i);
			label2.setText("PID - name");

			list.addAll(((ProcessQueue) queueArray[i]).stringArray());
			view.setItems(list);
			view.setOnMouseClicked(clickHandler);

			box.getChildren().addAll(label1, label2, view);

			queueViews.put(i, view);
			queueLists.put(i, list);
			queueLabels1.put(i, label1);
			queueLabels2.put(i, label2);
			queueBoxes.put(i, box);
			grid.add(box, i, 1);
		}
		selectedProcessLabel.setAlignment(Pos.CENTER);
		selectedProcessLabel.setFont(Font.font("Arial", 14));
		selectedProcessLabel.setText("Selected Process");
		selectedProcessBox.setPrefSize(175, 175);

		runningProcessLabel.setAlignment(Pos.CENTER);
		runningProcessLabel.setFont(Font.font("Arial", 14));
		runningProcessLabel.setText("Running Process");
		runningProcessBox.setPrefSize(175, 175);

		blockedLabel.setAlignment(Pos.CENTER);
		blockedLabel.setFont(Font.font("Arial", 14));
		blockedLabel.setText("Blocked Processes");
		blockedBox.setPrefSize(175, 300);
		blockedBox.getChildren().addAll(blockedLabel, blockedView);
		blockedView.setOnMouseClicked(clickHandler);
		grid.add(blockedBox, (maxPriority + 1), 1);

	}

	private void refreshRunningView() {
		runningProcess = dispatcher.getActiveProcess();
		runningProcessView.setItems(null);

		if (runningProcess != null) {
			runningProcessList.setAll(runningProcess.stringArray());
			runningProcessView.setItems(runningProcessList);
		}
	}

	private void refresh() {
		queueArray = dispatcher.queueArray();

		for (int i = 0; i <= maxPriority; i++) {
			ListView<String> view = queueViews.get(i);
			ObservableList<String> list = queueLists.get(i);
			Label label1 = queueLabels1.get(i);
			Label label2 = queueLabels2.get(i);
			VBox box = queueBoxes.get(i);

			list.setAll(((ProcessQueue) queueArray[i]).stringArray());
			view.setItems(list);
			box.getChildren().setAll(label1, label2, view);
		}
		blockedList.setAll(dispatcher.getBlockList());
		blockedView.setItems(blockedList);

		refreshRunningView();
	}

	@Override
	public void start(Stage stage) {
		stage.setTitle("Dispatcher Simulation");
		grid.setAlignment(Pos.CENTER);
		grid.setHgap(10);
		grid.setVgap(10);
		grid.setPadding(new Insets(25, 25, 25, 25));
		grid.setGridLinesVisible(false);

		final TextField createPriority = new TextField();
		final TextField createName = new TextField();

		Button createNewProcessButton = new Button("Create New Process");
		createNewProcessButton.setOnAction(new EventHandler<ActionEvent>() {
			public void handle(ActionEvent event) {
				String newPriority = createPriority.getText();
				String newName = createName.getText();

				newPriority = newPriority.trim();
				newName = newName.trim();

				if ((newPriority.length() != 0) && (newName.length() != 0)) {
					dispatcher.createProcess(Integer.parseInt(newPriority), newName);
				} else if (newPriority.length() != 0) {
					dispatcher.createProcess(Integer.parseInt(newPriority));
				} else if (newName.length() != 0) {
					dispatcher.createProcess(0, newName);
				} else {
					dispatcher.createProcess();
				}
				refresh();
			}
		});

		Button interruptButton = new Button("Interrupt");
		interruptButton.setOnAction(new EventHandler<ActionEvent>() {
			public void handle(ActionEvent event) {
				if (runningProcess != null) {
					dispatcher.queueProcess(runningProcess);
					runningProcess = null;
					refresh();
				}
			}
		});

		Button unblockButton = new Button("Unblock");
		unblockButton.setOnAction(new EventHandler<ActionEvent>() {
			public void handle(ActionEvent event) {
				if (selectedProcess != null) {
					if (selectedProcess.getState() == State.BLOCKED) {
						dispatcher.queueProcess(selectedProcess);
						refresh();
					}
				}
			}
		});

		Button blockButton = new Button("Block");
		blockButton.setOnAction(new EventHandler<ActionEvent>() {
			public void handle(ActionEvent event) {
				if (runningProcess != null) {
					dispatcher.blockProcess(runningProcess);
					runningProcess = null;
					refresh();
				}
			}
		});

		Button terminateButton = new Button("Terminate");
		terminateButton.setOnAction(new EventHandler<ActionEvent>() {
			public void handle(ActionEvent event) {
				if (selectedProcess != null) {
					dispatcher.killProcess(selectedProcess);
					selectedProcessView.setItems(null);
					refresh();
				}
			}
		});

		GridPane createArea = new GridPane();
		createArea.setHgap(10);
		createArea.setVgap(10);

		Label createNewLabel = new Label();
		createNewLabel.setText("Create a new process here");
		createNewLabel.setFont(Font.font("Arial", 14));
		createArea.add(createNewLabel, 0, 0, 2, 1);

		Text createHint = new Text("(Priority and name are optional)");
		createHint.setFont(Font.font("Arial", 12));
		createArea.add(createHint, 0, 1, 2, 1);

		Label createPriorityLabel = new Label();
		createPriorityLabel.setText("Priority:");
		createArea.add(createPriorityLabel, 0, 2);
		createArea.add(createPriority, 0, 3);

		Label createNameLabel = new Label();
		createNameLabel.setText("Process name:");
		createArea.add(createNameLabel, 1, 2);
		createArea.add(createName, 1, 3);

		createArea.add(createNewProcessButton, 0, 4, 2, 1);

		grid.add(createArea, 1, 0, 2, 1);

		HBox runningButtons = new HBox();
		runningButtons.getChildren().addAll(interruptButton, blockButton);

		HBox selectButtons = new HBox();
		selectButtons.getChildren().addAll(terminateButton, unblockButton);

		selectedProcessBox.getChildren().addAll(selectedProcessLabel, selectedProcessView, selectButtons);
		grid.add(selectedProcessBox, 3, 0);
		runningProcessBox.getChildren().addAll(runningProcessLabel, runningProcessView, runningButtons);
		grid.add(runningProcessBox, 0, 0);

		refresh();

		Scene scene = new Scene(grid, 800, 600);

		stage.setScene(scene);
		stage.show();
	}

	public static void main(String[] args) {
		launch(args);
	}
}
