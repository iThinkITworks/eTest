package com.etest.main;

import com.etest.view.CellCaseView;
import com.etest.view.SyllabusView;
import com.etest.view.HousekeepingView;
import com.etest.view.SemestralTeamView;
import com.etest.view.FacultyView;
import com.etest.view.CurriculumView;
import com.etest.service.UsersService;
import com.etest.serviceprovider.UsersServiceImpl;
import com.etest.valo.*;
import com.etest.view.DashboardView;
import com.etest.view.TQView;
import com.etest.view.testbank.*;
import javax.servlet.annotation.WebServlet;

import com.vaadin.annotations.Theme;
import com.vaadin.annotations.VaadinServletConfiguration;
import com.vaadin.annotations.Widgetset;
import com.vaadin.data.Container;
import com.vaadin.data.Item;
import com.vaadin.data.util.HierarchicalContainer;
import com.vaadin.data.util.IndexedContainer;
import com.vaadin.event.Action;
import com.vaadin.event.ShortcutAction;
import com.vaadin.navigator.Navigator;
import com.vaadin.navigator.ViewChangeListener;
import com.vaadin.server.FontAwesome;
import com.vaadin.server.Page;
import com.vaadin.server.Resource;
import com.vaadin.server.Responsive;
import com.vaadin.server.ThemeResource;
import com.vaadin.server.VaadinRequest;
import com.vaadin.server.VaadinServlet;
import com.vaadin.server.VaadinSession;
import com.vaadin.shared.Position;
import com.vaadin.shared.ui.label.ContentMode;
import com.vaadin.ui.Alignment;
import com.vaadin.ui.Button;
import com.vaadin.ui.Button.ClickEvent;
import com.vaadin.ui.Component;
import com.vaadin.ui.ComponentContainer;
import com.vaadin.ui.CssLayout;
import com.vaadin.ui.HorizontalLayout;
import com.vaadin.ui.Label;
import com.vaadin.ui.MenuBar;
import com.vaadin.ui.MenuBar.Command;
import com.vaadin.ui.Notification;
import com.vaadin.ui.Notification.Type;
import com.vaadin.ui.PasswordField;
import com.vaadin.ui.TextField;
import com.vaadin.ui.UI;
import static com.vaadin.ui.UI.getCurrent;
import com.vaadin.ui.VerticalLayout;
import com.vaadin.ui.Window;
import com.vaadin.ui.themes.ValoTheme;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.Locale;
import java.util.Map;
import javax.servlet.ServletException;

/**
 *
 */
@Theme("dashboard")
@Widgetset("com.etest.main.MyAppWidgetset")
public class MainUI extends UI {
private boolean testMode = false;

    @WebServlet(urlPatterns = "/*", name = "MyUIServlet", asyncSupported = true)
        @VaadinServletConfiguration(ui = MainUI.class, productionMode = false)
        public static class MyUIServlet extends VaadinServlet {
            @Override
            protected void servletInitialized() throws ServletException {
                super.servletInitialized();
                getService().addSessionInitListener(
                        new ValoThemeSessionInitListener());
        }
    }
    
    private static LinkedHashMap<String, String> themeVariants = new LinkedHashMap<String, String>();
    static {
        themeVariants.put("tests-valo", "Default");
        themeVariants.put("tests-valo-blueprint", "Blueprint");
        themeVariants.put("tests-valo-dark", "Dark");
        themeVariants.put("tests-valo-facebook", "Facebook");
        themeVariants.put("tests-valo-flatdark", "Flat dark");
        themeVariants.put("tests-valo-flat", "Flat");
        themeVariants.put("tests-valo-light", "Light");
        themeVariants.put("tests-valo-metro", "Metro");
    }
    private final TestIcon testIcon = new TestIcon(100);

    ValoMenuLayout root = new ValoMenuLayout();
    ComponentContainer viewDisplay = root.getContentContainer();
    CssLayout menu = new CssLayout();
    CssLayout menuItemsLayout = new CssLayout();
    {
        menu.setId("testMenu");
    }
    private Navigator navigator;
    private final LinkedHashMap<String, String> menuItems = new LinkedHashMap<String, String>(); 
    
    @Override
    protected void init(final VaadinRequest request) {
//        setTheme("dashboard");
        setImmediate(true);
        setLocale(Locale.US);

//        Responsive.makeResponsive(this);
        addStyleName(ValoTheme.UI_WITH_MENU);
        
        if (request.getParameter("test") != null) {
            testMode = true;

            if (browserCantRenderFontsConsistently()) {
                getPage().getStyles().add(
                        ".v-app.v-app.v-app {font-family: Sans-Serif;}");
            }
        }

        if (getPage().getWebBrowser().isIE()
                && getPage().getWebBrowser().getBrowserMajorVersion() == 9) {
            menu.setWidth("200px");
        } else {
            menu.setWidth("300px");
        }
        // Show .v-app-loading valo-menu-badge
        // try {
        // Thread.sleep(2000);
        // } catch (InterruptedException e) {
        // e.printStackTrace();
        // }

        if (!testMode) {
            Responsive.makeResponsive(this);
        }
        
        getPage().setTitle("eTest Generator");
        
//        setContent(root);
//        root.setWidth("100%");
//        root.addMenu(buildMenu());
        
        Window sub = loginWindow();
        if(sub.getParent() == null){
            UI.getCurrent().addWindow(sub);
        }
        sub.center();
        sub.setModal(true);        
        sub.addCloseListener(loginWindowCloseListener);
   
        navigator = new Navigator(this, viewDisplay);

        navigator.addView("dashboard", DashboardView.class);
        navigator.addView("curriculum", CurriculumView.class);
        navigator.addView("faculty-member", FacultyView.class);
        navigator.addView("syllabus", SyllabusView.class);
        navigator.addView("semestral-team", SemestralTeamView.class);
        navigator.addView("housekeeping", HousekeepingView.class);
        navigator.addView("cells", CellCaseView.class);
        navigator.addView("run-item-analysis", RunItemAnalysisView.class);
        navigator.addView("tq", TQView.class);
        
//        navigator.addView("common", CommonParts.class);
//        navigator.addView("labels", Labels.class);
//        navigator.addView("buttons-and-links", ButtonsAndLinks.class);
//        navigator.addView("textfields", TextFields.class);
//        navigator.addView("datefields", DateFields.class);
//        navigator.addView("comboboxes", ComboBoxes.class);
//        navigator.addView("checkboxes", CheckBoxes.class);
//        navigator.addView("sliders", Sliders.class);
//        navigator.addView("menubars", MenuBars.class);
//        navigator.addView("panels", Panels.class);
//        navigator.addView("trees", Trees.class);
//        navigator.addView("tables", Tables.class);
//        navigator.addView("splitpanels", SplitPanels.class);
//        navigator.addView("tabs", Tabsheets.class);
//        navigator.addView("accordions", Accordions.class);
//        navigator.addView("colorpickers", ColorPickers.class);
//        navigator.addView("selects", NativeSelects.class);
//        navigator.addView("calendar", CalendarTest.class);
//        navigator.addView("forms", Forms.class);
//        navigator.addView("popupviews", PopupViews.class);
//        navigator.addView("dragging", Dragging.class);

        final String f = Page.getCurrent().getUriFragment();
        if (f == null || f.equals("")) {
            navigator.navigateTo("dashboard");
        }

        navigator.setErrorView(DashboardView.class);

        navigator.addViewChangeListener(new ViewChangeListener() {

            @Override
            public boolean beforeViewChange(final ViewChangeListener.ViewChangeEvent event) {
                return true;
            }

            @Override
            public void afterViewChange(final ViewChangeListener.ViewChangeEvent event) {
                for (final Iterator<Component> it = menuItemsLayout.iterator(); it
                        .hasNext();) {
                    it.next().removeStyleName("selected");
                }
                for (final Map.Entry<String, String> item : menuItems.entrySet()) {
                    if (event.getViewName().equals(item.getKey())) {
                        for (final Iterator<Component> it = menuItemsLayout
                                .iterator(); it.hasNext();) {
                            final Component c = it.next();
                            if (c.getCaption() != null
                                    && c.getCaption().startsWith(
                                            item.getValue())) {
                                c.addStyleName("selected");
                                break;
                            }
                        }
                        break;
                    }
                }
                menu.removeStyleName("valo-menu-visible");
            }
        });
    }

    private boolean browserCantRenderFontsConsistently() {
        // PhantomJS renders font correctly about 50% of the time, so
        // disable it to have consistent screenshots
        // https://github.com/ariya/phantomjs/issues/10592

        // IE8 also has randomness in its font rendering...

        return getPage().getWebBrowser().getBrowserApplication()
                .contains("PhantomJS")
                || (getPage().getWebBrowser().isIE() && getPage()
                        .getWebBrowser().getBrowserMajorVersion() <= 9);
    }
    
    public static boolean isTestMode() {
        return ((MainUI) getCurrent()).testMode;
    }
    
    Component buildTestMenu() {
        final CssLayout menu = new CssLayout();
        menu.addStyleName("large-icons");

        final Label logo = new Label("Va");
        logo.setSizeUndefined();
        logo.setPrimaryStyleName("valo-menu-logo");
        menu.addComponent(logo);

        Button b = new Button(
                "Reference <span class=\"valo-menu-badge\">3</span>");
        b.setIcon(FontAwesome.TH_LIST);
        b.setPrimaryStyleName("valo-menu-item");
        b.addStyleName("selected");
        b.setHtmlContentAllowed(true);
        menu.addComponent(b);

        b = new Button("API");
        b.setIcon(FontAwesome.BOOK);
        b.setPrimaryStyleName("valo-menu-item");
        menu.addComponent(b);

        b = new Button("Examples <span class=\"valo-menu-badge\">12</span>");
        b.setIcon(FontAwesome.TABLE);
        b.setPrimaryStyleName("valo-menu-item");
        b.setHtmlContentAllowed(true);
        menu.addComponent(b);

        return menu;
    }
    
    CssLayout buildMenu() {
        // Add items
        menuItems.put("dashboard", "Dashboard");
        menuItems.put("curriculum", "Curriculum");
        menuItems.put("faculty-member", "Faculty Member");
        menuItems.put("syllabus", "Syllabus");
        menuItems.put("semestral-team", "Semestral Team");
        menuItems.put("housekeeping", "Housekeeping");
        menuItems.put("cells", "Create/Modify/Approve Cells");
        menuItems.put("run-item-analysis", "Run Item Analysis");
        menuItems.put("tq", "Test Questionnaire");
        
//        menuItems.put("common", "Dashboard");
//        menuItems.put("labels", "Labels");
//        menuItems.put("buttons-and-links", "Buttons & Links");
//        menuItems.put("textfields", "Text Fields");
//        menuItems.put("datefields", "Date Fields");
//        menuItems.put("comboboxes", "Combo Boxes");
//        menuItems.put("selects", "Selects");
//        menuItems.put("checkboxes", "Check Boxes & Option Groups");
//        menuItems.put("sliders", "Sliders & Progress Bars");
//        menuItems.put("colorpickers", "Color Pickers");
//        menuItems.put("menubars", "Menu Bars");
//        menuItems.put("trees", "Trees");
//        menuItems.put("tables", "Tables");
//        menuItems.put("dragging", "Drag and Drop");
//        menuItems.put("panels", "Panels");
//        menuItems.put("splitpanels", "Split Panels");
//        menuItems.put("tabs", "Tabs");
//        menuItems.put("accordions", "Accordions");
//        menuItems.put("popupviews", "Popup Views");
//        menuItems.put("calendar", "Calendar");
//        menuItems.put("forms", "Forms");

        final HorizontalLayout top = new HorizontalLayout();
        top.setWidth("100%");
        top.setDefaultComponentAlignment(Alignment.MIDDLE_LEFT);
        top.addStyleName("valo-menu-title");
        menu.addComponent(top);
//        menu.addComponent(createThemeSelect());

        final Button showMenu = new Button("Menu", new Button.ClickListener() {
            @Override
            public void buttonClick(final ClickEvent event) {
                if (menu.getStyleName().contains("valo-menu-visible")) {
                    menu.removeStyleName("valo-menu-visible");
                } else {
                    menu.addStyleName("valo-menu-visible");
                }
            }
        });
        showMenu.addStyleName(ValoTheme.BUTTON_PRIMARY);
        showMenu.addStyleName(ValoTheme.BUTTON_SMALL);
        showMenu.addStyleName("valo-menu-toggle");
        showMenu.setIcon(FontAwesome.LIST);
        menu.addComponent(showMenu);

        final Label title = new Label(
                "<h3>Siliman<strong> University</strong></h3>", ContentMode.HTML);
        title.setSizeUndefined();
        top.addComponent(title);
        top.setExpandRatio(title, 1);
        
        final MenuBar settings = new MenuBar();
        settings.addStyleName("user-menu");
        final MenuBar.MenuItem settingsItem = settings.addItem(VaadinSession.getCurrent().getAttribute("username").toString(),
                new ThemeResource("../tests-valo/img/profile-pic-300px.jpg"),
                null);
//        final MenuBar.MenuItem settingsItem = settings.addItem("Admin",
//                new ThemeResource("../tests-valo/img/profile-pic-300px.jpg"),
//                null);
        settingsItem.addItem("Edit Profile", menuCommand);
        settingsItem.addItem("Preferences", menuCommand);
        settingsItem.addSeparator();
        settingsItem.addItem("Sign Out", menuCommand);
        menu.addComponent(settings);

        menuItemsLayout.setPrimaryStyleName("valo-menuitems");
        menu.addComponent(menuItemsLayout);

        Label label = null;
        int count = -1;
        for (final Map.Entry<String, String> item : menuItems.entrySet()) {
            if (item.getKey().equals("curriculum")) {
                label = new Label("System Administration", ContentMode.HTML);
                label.setPrimaryStyleName("valo-menu-subtitle");
                label.addStyleName("h4");
                label.setSizeUndefined();
                menuItemsLayout.addComponent(label);
            }
            
            if (item.getKey().equals("cells")) {
                label = new Label("Test Bank", ContentMode.HTML);
                label.setPrimaryStyleName("valo-menu-subtitle");
                label.addStyleName("h4");
                label.setSizeUndefined();
                menuItemsLayout.addComponent(label);
            }
            
//            if (item.getKey().equals("labels")) {
//                label = new Label("eTest Modules", ContentMode.HTML);
//                label.setPrimaryStyleName("valo-menu-subtitle");
//                label.addStyleName("h4");
//                label.setSizeUndefined();
//                menuItemsLayout.addComponent(label);
//            }
//            
//            if (item.getKey().equals("panels")) {
//                label.setValue(label.getValue()
//                        + " <span class=\"valo-menu-badge\">" + count
//                        + "</span>");
//                count = 0;
//                label = new Label("Community", ContentMode.HTML);
//                label.setPrimaryStyleName("valo-menu-subtitle");
//                label.addStyleName("h4");
//                label.setSizeUndefined();
//                menuItemsLayout.addComponent(label);
//            }
//            
//            if (item.getKey().equals("forms")) {
//                label.setValue(label.getValue()
//                        + " <span class=\"valo-menu-badge\">" + count
//                        + "</span>");
//                count = 0;
//                label = new Label("Other", ContentMode.HTML);
//                label.setPrimaryStyleName("valo-menu-subtitle");
//                label.addStyleName("h4");
//                label.setSizeUndefined();
//                menuItemsLayout.addComponent(label);
//            }
            
            final Button b = new Button(item.getValue(), (final ClickEvent event) -> {
                navigator.navigateTo(item.getKey());
            });
            
//            if (count == 2) {
//                b.setCaption(b.getCaption()
//                        + " <span class=\"valo-menu-badge\">123</span>");
//            }
            
            b.setHtmlContentAllowed(true);
            b.setPrimaryStyleName("valo-menu-item");
            b.setIcon(testIcon.get());
            menuItemsLayout.addComponent(b);
            count++;
        }
//        label.setValue(label.getValue() + " <span class=\"valo-menu-badge\">"
//                + count + "</span>");

        return menu;
    }
    
//    private Component createThemeSelect() {
//        final NativeSelect ns = new NativeSelect();
//        ns.setNullSelectionAllowed(false);
//        ns.setId("themeSelect");
//        ns.addContainerProperty("caption", String.class, "");
//        ns.setItemCaptionPropertyId("caption");
//        for (final String identifier : themeVariants.keySet()) {
//            ns.addItem(identifier).getItemProperty("caption")
//                    .setValue(themeVariants.get(identifier));
//        }
//
//        ns.setValue("tests-valo");
//        ns.addValueChangeListener(new Property.ValueChangeListener() {
//            @Override
//            public void valueChange(final Property.ValueChangeEvent event) {
//                setTheme((String) ns.getValue());
//            }
//        });
//        return ns;
//    }

    static Action.Handler actionHandler = new Action.Handler() {
        private final Action ACTION_ONE = new Action("Action One");
        private final Action ACTION_TWO = new Action("Action Two");
        private final Action ACTION_THREE = new Action("Action Three");
        private final Action[] ACTIONS = new Action[] { ACTION_ONE, ACTION_TWO,
                ACTION_THREE };

        @Override
        public void handleAction(final Action action, final Object sender,
                final Object target) {
            Notification.show(action.getCaption());
        }

        @Override
        public Action[] getActions(final Object target, final Object sender) {
            return ACTIONS;
        }
    };

    public static Action.Handler getActionHandler() {
        return actionHandler;
    }

    public static final String CAPTION_PROPERTY = "caption";
    public static final String DESCRIPTION_PROPERTY = "description";
    public static final String ICON_PROPERTY = "icon";
    public static final String INDEX_PROPERTY = "index";

    @SuppressWarnings("unchecked")
    public static Container generateContainer(final int size,
            final boolean hierarchical) {
        final TestIcon testIcon = new TestIcon(90);
        final IndexedContainer container = hierarchical ? new HierarchicalContainer()
                : new IndexedContainer();
        final StringGenerator sg = new StringGenerator();
        container.addContainerProperty(CAPTION_PROPERTY, String.class, null);
        container.addContainerProperty(ICON_PROPERTY, Resource.class, null);
        container.addContainerProperty(INDEX_PROPERTY, Integer.class, null);
        container
                .addContainerProperty(DESCRIPTION_PROPERTY, String.class, null);
        for (int i = 1; i < size + 1; i++) {
            final Item item = container.addItem(i);
            item.getItemProperty(CAPTION_PROPERTY).setValue(
                    sg.nextString(true) + " " + sg.nextString(false));
            item.getItemProperty(INDEX_PROPERTY).setValue(i);
            item.getItemProperty(DESCRIPTION_PROPERTY).setValue(
                    sg.nextString(true) + " " + sg.nextString(false) + " "
                            + sg.nextString(false));
            item.getItemProperty(ICON_PROPERTY).setValue(testIcon.get());
        }
        container.getItem(container.getIdByIndex(0))
                .getItemProperty(ICON_PROPERTY).setValue(testIcon.get());

        if (hierarchical) {
            for (int i = 1; i < size + 1; i++) {
                for (int j = 1; j < 5; j++) {
                    final String id = i + " -> " + j;
                    Item child = container.addItem(id);
                    child.getItemProperty(CAPTION_PROPERTY).setValue(
                            sg.nextString(true) + " " + sg.nextString(false));
                    child.getItemProperty(ICON_PROPERTY).setValue(
                            testIcon.get());
                    // ((Hierarchical) container).setChildrenAllowed(id, false);
                    ((Container.Hierarchical) container).setParent(id, i);

                    for (int k = 1; k < 6; k++) {
                        final String id2 = id + " -> " + k;
                        child = container.addItem(id2);
                        child.getItemProperty(CAPTION_PROPERTY).setValue(
                                sg.nextString(true) + " "
                                        + sg.nextString(false));
                        child.getItemProperty(ICON_PROPERTY).setValue(
                                testIcon.get());
                        // ((Hierarchical) container)
                        // .setChildrenAllowed(id, false);
                        ((Container.Hierarchical) container).setParent(id2, id);

                        for (int l = 1; l < 5; l++) {
                            final String id3 = id2 + " -> " + l;
                            child = container.addItem(id3);
                            child.getItemProperty(CAPTION_PROPERTY).setValue(
                                    sg.nextString(true) + " "
                                            + sg.nextString(false));
                            child.getItemProperty(ICON_PROPERTY).setValue(
                                    testIcon.get());
                            // ((Hierarchical) container)
                            // .setChildrenAllowed(id, false);
                            ((Container.Hierarchical) container).setParent(id3, id2);
                        }
                    }
                }
            }
        }
        return container;
    }    
    
    private final Command menuCommand = new Command(){

        @Override
        public void menuSelected(MenuBar.MenuItem selectedItem) {
            Notification.show("Action " + selectedItem.getText(),
                    Type.TRAY_NOTIFICATION);
            if(selectedItem.getText().equals("Sign Out")){
                VaadinSession.getCurrent().close();
                Page.getCurrent().reload();
            }
        }
        
    };

    private Window loginWindow(){        
        VerticalLayout vlayout = new VerticalLayout();
        vlayout.setSizeFull();
        vlayout.setSpacing(true);
        vlayout.setMargin(true);
        
        final Window sub = new Window("", vlayout);
        sub.setSizeFull();
        sub.setResizable(false);
        sub.setClosable(false);
        
        Component loginForm = buildLoginForm(sub);
        vlayout.addComponent(loginForm);
        vlayout.setComponentAlignment(loginForm, Alignment.MIDDLE_CENTER);
        sub.setContent(vlayout);
        
        Notification notification = new Notification(
                "Welcome to eTest Generator");
        notification
                .setDescription("<span>This application is not real, it only demonstrates an application built with the <a href=\"https://vaadin.com\">Vaadin framework</a>.</span> <span>Close this <b>Notification</b> to continue.</span>");
        notification.setHtmlContentAllowed(true);
        notification.setStyleName("tray dark small closable login-help");
        notification.setPosition(Position.BOTTOM_CENTER);
        notification.show(Page.getCurrent());
        notification.setDelayMsec(-1);
        
        return sub;
    }

    private Component buildLoginForm(Window sub) {        
        final VerticalLayout loginPanel = new VerticalLayout();
        loginPanel.setSizeUndefined();
        loginPanel.setSpacing(true);
        Responsive.makeResponsive(loginPanel);
        loginPanel.addStyleName("login-panel");

        loginPanel.addComponent(buildLabels());
        loginPanel.addComponent(buildFields(sub));
//        loginPanel.addComponent(new CheckBox("Remember me", true));
        return loginPanel;
    }
    
    private Component buildFields(final Window sub) {
        final UsersService userLoginService = new UsersServiceImpl();
        HorizontalLayout fields = new HorizontalLayout();
        fields.setSpacing(true);
        fields.addStyleName("fields");

        final TextField username = new TextField("Username");
        username.setIcon(FontAwesome.USER);
        username.addStyleName(ValoTheme.TEXTFIELD_INLINE_ICON);

        final PasswordField password = new PasswordField("Password");
        password.setIcon(FontAwesome.LOCK);
        password.addStyleName(ValoTheme.TEXTFIELD_INLINE_ICON);

        final Button signin = new Button("Sign In");
        signin.addStyleName(ValoTheme.BUTTON_PRIMARY);
        signin.setClickShortcut(ShortcutAction.KeyCode.ENTER);
        signin.focus();

        fields.addComponents(username, password, signin);
        fields.setComponentAlignment(signin, Alignment.BOTTOM_LEFT);

        signin.addClickListener(new Button.ClickListener() {
            @Override
            public void buttonClick(final Button.ClickEvent event) {
                if(username.getValue().isEmpty() || username.getValue() == null){
                    Notification.show("provide a username", Notification.Type.WARNING_MESSAGE);
                    return;
                }
                
                if(password.getValue().isEmpty() || password.getValue() == null){
                    Notification.show("password is required", Notification.Type.WARNING_MESSAGE);
                    return;
                }
                
                boolean result = userLoginService.loginResult(username.getValue(), password.getValue());
                if(!result){                    
                    return;
                } else {
//                    sub.setClosable(true);
                    sub.close();
                }
            }
        });
        return fields;
    }
    
    private Component buildLabels() {
        CssLayout labels = new CssLayout();
        labels.addStyleName("labels");

        Label welcome = new Label("Welcome");
        welcome.setSizeUndefined();
        welcome.addStyleName(ValoTheme.LABEL_H4);
        welcome.addStyleName(ValoTheme.LABEL_COLORED);
        labels.addComponent(welcome);

        Label title = new Label("eTest Dashboard");
        title.setSizeUndefined();
        title.addStyleName(ValoTheme.LABEL_H3);
        title.addStyleName(ValoTheme.LABEL_LIGHT);
        labels.addComponent(title);
        return labels;
    }
    
    Window.CloseListener loginWindowCloseListener = new Window.CloseListener() {

        @Override
        public void windowClose(Window.CloseEvent e) {
            setContent(root);
            root.setWidth("100%");
            root.addMenu(buildMenu());
            Notification.show("WELCOME "+VaadinSession.getCurrent().getAttribute("username"));
        }
    };
}
