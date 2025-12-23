package network.repository.jfxlibs.modules.ribbon;

import javafx.scene.layout.HBox;
import javafx.scene.layout.Priority;
import javafx.scene.layout.VBox;
import javafx.scene.text.Text;

import java.util.ArrayList;

public class Group extends VBox {

    private HBox content = new HBox();
    private HBox titleBar = new HBox();
    private ArrayList<Section> sections = new ArrayList<>();
    public final String title;
    public Group(String title) {
        this.getStyleClass().add("group");
        this.titleBar.getChildren().add(new Text(" - "+title+" - "));
        this.title = title;
        getChildren().add(content);
        getChildren().add(titleBar);
        VBox.setVgrow(content, Priority.ALWAYS);
        content.getStyleClass().add("content");
        titleBar.getStyleClass().add("titleBar");
    }

    public void add(Item item){
        switch (item.type){
            case LARGE, LARGE_DROPDOWN, LARGE_WITH_DROPDOWN -> {
                for (Section section : sections){
                    if (section.type != Section.Type.LARGE){continue;}
                    if (!section.isFull()){
                        section.add(item);
                        return;
                    }
                }
                Section section = new Section(Section.Type.LARGE);
                section.add(item);
                sections.add(section);
                content.getChildren().add(section);}

            case SMALL, SMALL_DROPDOWN, SMALL_WITH_DROPDOWN -> {
                for (Section section : sections){
                    if (section.type != Section.Type.SMALL){continue;}
                    if (!section.isFull()){
                        section.add(item);
                        return;
                    }
                }
                Section section = new Section(Section.Type.SMALL);
                section.add(item);
                sections.add(section);
                content.getChildren().add(section);
            }
        }
    }




}
