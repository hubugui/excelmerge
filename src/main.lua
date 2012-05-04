function set_look_feel()
	uiManager = luajava.bindClass("javax.swing.UIManager")
	uiManager:setLookAndFeel(uiManager:getSystemLookAndFeelClassName())
end

function build_frame()
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

	frame = luajava.newInstance("javax.swing.JFrame", "ReportMerge")

	borderLayout = luajava.bindClass("java.awt.BorderLayout")
	frame:getContentPane():add(pane, borderLayout.CENTER)

	jframe = luajava.bindClass("javax.swing.JFrame")
	frame:setDefaultCloseOperation(jframe.EXIT_ON_CLOSE)

	frame:setSize(800, 600)
	frame:setLocationRelativeTo(nil)
	frame:pack()
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
end

function build_toolbar()
end

function build_panel()
end

function main()
	set_look_feel()
	build_frame()
	build_menu()
	build_toolbar()
	build_panel()
end

main()
