local frame;
local menu;
local tree;

function set_look_feel()
	uiManager = luajava.bindClass("javax.swing.UIManager")
	uiManager:setLookAndFeel(uiManager:getSystemLookAndFeelClassName())
end

function build_frame(title)
	pane = luajava.newInstance("javax.swing.JPanel")
	borderFactory = luajava.bindClass("javax.swing.BorderFactory")
	border = borderFactory:createEmptyBorder(30, 30, 10, 30)
	pane:setBorder(border)

	layout = luajava.newInstance("java.awt.GridLayout", 2, 2);
	pane:setLayout(layout)

	label = luajava.newInstance("javax.swing.JLabel", "This is a Label");
	label:setText(luajava.newInstance("java.lang.String", "中"))

	pane:add(label)
	pane:setBounds(20, 30, 10, 30)

	frame = luajava.newInstance("javax.swing.JFrame", title)

	borderLayout = luajava.bindClass("java.awt.BorderLayout")
	frame:getContentPane():add(pane, borderLayout.CENTER)

	jframe = luajava.bindClass("javax.swing.JFrame")
	frame:setDefaultCloseOperation(jframe.EXIT_ON_CLOSE)

	frame:setSize(800, 600)
	frame:setLocationRelativeTo(nil)
	frame:setVisible(true)

	local listener = luajava.createProxy("java.awt.event.MouseListener",
	{
		mouseClicked = function(me)
			print("clicked!", me)
		end
	})

	frame:addMouseListener(listener)
end

function build_menu()
	menubar = luajava.newInstance("java.awt.MenuBar")

	menu_item_new = luajava.newInstance("java.awt.MenuItem", "打开")
	menu_file = luajava.newInstance("java.awt.Menu","文件")
	menu_file:add(menu_item_new)
	menu_file:addSeparator()
	menubar:add(menu_file)

	menu_item_manual = luajava.newInstance("java.awt.MenuItem", "手册")
	menu_item_about = luajava.newInstance("java.awt.MenuItem", "关于")
	menu_help = luajava.newInstance("java.awt.Menu", "帮助")
	menu_help:add(menu_item_manual)
	menu_help:add(menu_item_about)


	menubar:add(menu_help)
	frame:setMenuBar(menubar)
end

function build_toolbar()
	toolbar = luajava.newInstance("javax.swing.JToolBar")
	boolean = luajava.bindClass("java.lang.Boolean")

	button = luajava.newInstance("javax.swing.JButton")
    button:setActionCommand("打开")
    toolbar:add(button)
	toolbar:addSeparator()

	frame:add(toolbar, luajava.newInstance("java.awt.BorderLayout"):PAGE_START)
end

function build_panel()
end

function main()
	set_look_feel()
	build_frame('excel merge')
	build_menu()
	build_toolbar()
	build_panel()
end

main()
