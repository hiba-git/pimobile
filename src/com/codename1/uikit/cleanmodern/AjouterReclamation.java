/*
 * Copyright (c) 2016, Codename One
 *
 * Permission is hereby granted, free of charge, to any person obtaining a copy of this software and associated 
 * documentation files (the "Software"), to deal in the Software without restriction, including without limitation 
 * the rights to use, copy, modify, merge, publish, distribute, sublicense, and/or sell copies of the Software, 
 * and to permit persons to whom the Software is furnished to do so, subject to the following conditions:
 *
 * The above copyright notice and this permission notice shall be included in all copies or substantial portions 
 * of the Software.
 *
 * THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR IMPLIED, 
 * INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY, FITNESS FOR A 
 * PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE AUTHORS OR COPYRIGHT 
 * HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER LIABILITY, WHETHER IN AN ACTION OF 
 * CONTRACT, TORT OR OTHERWISE, ARISING FROM, OUT OF OR IN CONNECTION WITH THE SOFTWARE 
 * OR THE USE OR OTHER DEALINGS IN THE SOFTWARE. 
 */
package com.codename1.uikit.cleanmodern;

import com.codename1.components.ScaleImageLabel;
import com.codename1.components.SpanLabel;
import com.codename1.components.ToastBar;
import com.codename1.ui.Button;
import com.codename1.ui.ButtonGroup;
import com.codename1.ui.ComboBox;
import com.codename1.ui.Component;
import com.codename1.ui.Container;
import com.codename1.ui.Display;
import com.codename1.ui.FontImage;
import com.codename1.ui.Graphics;
import com.codename1.ui.Image;
import com.codename1.ui.Label;
import com.codename1.ui.RadioButton;
import com.codename1.ui.Tabs;
import com.codename1.ui.TextArea;
import com.codename1.ui.Toolbar;
import com.codename1.ui.layouts.BorderLayout;
import com.codename1.ui.layouts.BoxLayout;
import com.codename1.ui.layouts.FlowLayout;
import com.codename1.ui.layouts.GridLayout;
import com.codename1.ui.layouts.LayeredLayout;
import com.codename1.ui.plaf.Style;
import com.codename1.ui.util.Resources;
import entities.Question;
import java.util.List;
import services.reclamationService;

/**
 * The newsfeed form
 *
 * @author Shai Almog
 */
public class AjouterReclamation extends BaseForm {

    public AjouterReclamation(Resources res) {
        super("Reclamation", BoxLayout.y());
        Toolbar tb = new Toolbar(true);
        setToolbar(tb);
        getTitleArea().setUIID("Container");
        setTitle("Newsfeed");
        getContentPane().setScrollVisible(false);
        super.addSideMenu(res);
        Tabs swipe = new Tabs();
        reclamationService service = new reclamationService();
        List<Question> listEvenements = service.getAllTasks();
        Label spacer1 = new Label();
        Label spacer2 = new Label();
        addTab(swipe, res.getImage("VISU-RECLAMATION-BLOG.jpg"), spacer1);

        swipe.setUIID("Container");
        swipe.getContentPane().setUIID("Container");
        swipe.hideTabs();

        ButtonGroup bg = new ButtonGroup();
        int size = Display.getInstance().convertToPixels(1);
        Image unselectedWalkthru = Image.createImage(size, size, 0);
        Graphics g = unselectedWalkthru.getGraphics();
        g.setColor(0xffffff);
        g.setAlpha(100);
        g.setAntiAliased(true);
        g.fillArc(0, 0, size, size, 0, 360);
        Image selectedWalkthru = Image.createImage(size, size, 0);
        g = selectedWalkthru.getGraphics();
        g.setColor(0xffffff);
        g.setAntiAliased(true);
        g.fillArc(0, 0, size, size, 0, 360);
        RadioButton[] rbs = new RadioButton[swipe.getTabCount()];
        FlowLayout flow = new FlowLayout(CENTER);
        flow.setValign(BOTTOM);
        Container radioContainer = new Container(flow);
        for (int iter = 0; iter < rbs.length; iter++) {
            rbs[iter] = RadioButton.createToggle(unselectedWalkthru, bg);
            rbs[iter].setPressedIcon(selectedWalkthru);
            rbs[iter].setUIID("Label");
            radioContainer.add(rbs[iter]);
        }

        rbs[0].setSelected(true);
        swipe.addSelectionListener((i, ii) -> {
            if (!rbs[ii].isSelected()) {
                rbs[ii].setSelected(true);
            }
        });

        Component.setSameSize(radioContainer, spacer1, spacer2);
        add(LayeredLayout.encloseIn(swipe, radioContainer));

        ButtonGroup barGroup = new ButtonGroup();
        RadioButton all = RadioButton.createToggle("Récents questions", barGroup);
        all.setUIID("SelectBar");
        all.addActionListener(e -> {
            new ReclamationForm(res).show();
        });
        RadioButton featured = RadioButton.createToggle("Anciens", barGroup);
        featured.setUIID("SelectBar");
        featured.addActionListener(e -> {
            new AncienReclamtionForm(res).show();
        });
        RadioButton popular = RadioButton.createToggle("Ajouter reclamation", barGroup);
        popular.setUIID("SelectBar");
        popular.addActionListener(e -> {
            new AjouterReclamation(res).show();
        });
        Label arrow = new Label(res.getImage("news-tab-down-arrow.png"), "Container");

        add(LayeredLayout.encloseIn(
                GridLayout.encloseIn(3, all, featured, popular),
                FlowLayout.encloseBottom(arrow)
        ));

        popular.setSelected(true);
        arrow.setVisible(false);
        addShowListener(e -> {
            arrow.setVisible(true);
            updateArrowPosition(popular, arrow);
        });
        bindButtonSelection(all, arrow);
        bindButtonSelection(featured, arrow);
        bindButtonSelection(popular, arrow);
        addOrientationListener(e -> {
            updateArrowPosition(barGroup.getRadioButton(barGroup.getSelectedIndex()), arrow);
        });
        
            addButton(res.getImage("rec.jpg"), false);
        
    }

    private void updateArrowPosition(Button b, Label arrow) {
        arrow.getUnselectedStyle().setMargin(LEFT, b.getX() + b.getWidth() / 2 - arrow.getWidth() / 2);
        arrow.getParent().repaint();

    }

    private void addTab(Tabs swipe, Image img, Label spacer) {
        int size = Math.min(Display.getInstance().getDisplayWidth(), Display.getInstance().getDisplayHeight());
        if (img.getHeight() < size) {
            img = img.scaledHeight(size);
        }

        if (img.getHeight() > Display.getInstance().getDisplayHeight() / 2) {
            img = img.scaledHeight(Display.getInstance().getDisplayHeight() / 2);
        }
        ScaleImageLabel image = new ScaleImageLabel(img);
        image.setUIID("Container");
        image.setBackgroundType(Style.BACKGROUND_IMAGE_SCALED_FILL);
        Label overlay = new Label(" ", "ImageOverlay");

        Container page1
                = LayeredLayout.encloseIn(
                        image,
                        overlay,
                        BorderLayout.south(
                                BoxLayout.encloseY(
                                        spacer
                                )
                        )
                );

        swipe.addTab("", page1);
    }

    private void addButton(Image img,  boolean liked) {
        ComboBox<String>  Type=new ComboBox();
        Type.addItem("Evenement");
        Type.addItem("Cours");
        Type.addItem("Accessoires");
        Type.addItem("Produit");
        Type.addItem("Personel");
        add(Type);
        TextArea t =new TextArea(5, 10);
        add(t);
        Button b =new Button("Valider");
        add(b);
        b.addActionListener(e->{
            Question quest =new Question();
            quest.setMessage(t.getText());
            if(Type.getSelectedIndex()==0){
                quest.setType("Evenement");
            }
            if(Type.getSelectedIndex()==1){
                quest.setType("Cours");
            }
            if(Type.getSelectedIndex()==2){
                quest.setType("Accessoires");
            }
            if(Type.getSelectedIndex()==3){
                quest.setType("Produit");
            }
            if(Type.getSelectedIndex()==4){
                quest.setType("Personel");
            }
            reclamationService service =new reclamationService();
//            System.out.println("waaaa"+SessionManager.getUsername());
            System.out.println("waaaa"+SessionManager.getUserName());
            service.AjouterQuestion(quest,SessionManager.getUserName());
           SendSMS.sendSMSreservation();
            ToastBar.showMessage("Nous avons enregistre votre reclamation", FontImage.MATERIAL_INFO);
        });
    }

    private void bindButtonSelection(Button b, Label arrow) {
        b.addActionListener(e -> {
            if (b.isSelected()) {
                updateArrowPosition(b, arrow);
            }
        });
    }
    
}
