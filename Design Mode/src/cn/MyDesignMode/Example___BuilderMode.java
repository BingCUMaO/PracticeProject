package cn.MyDesignMode;

/**
 * 	模拟计算机组装过程
 * 	部件：主板、操作系统、显示器
 * @author BinGCU
 *
 */
public class Example___BuilderMode {
	public static void main(String[] args) {
		MacBookAssemblyLine macAL = new MacBookAssemblyLine();
		PersonalComputer macComputer;
		macAL.buildMacBoard();
		macAL.buildMacOS();
		macAL.buildMacDisplayer();
		macComputer = macAL.assemble();
		System.out.println(macComputer);
//		macAL.buildMacBoard();
//		macAL.buildMacOS();
//		macAL.buildMacDisplayer();
		macComputer = macAL.assemble();
		System.out.println(macComputer);
		
		System.out.println("#####");
		
		DellBookAssemblyLine dellAL = new DellBookAssemblyLine();
		PersonalComputer dellComputer;
		dellAL.buildDellBoard();
		dellAL.buildWindowsOS();
		dellAL.buildDellDisplayer();
		dellComputer = dellAL.assemble();
		System.out.println(dellComputer);
		dellAL.buildDellBoard();
		dellAL.buildWindowsOS();
		dellAL.buildDellDisplayer();
		dellComputer = dellAL.assemble();
		System.out.println(dellComputer);
	}
}

/*
 * 	部件
 * */
interface Board{
}
interface OS{
}
interface Displayer{
}

/*
 * 	个人电脑
 * */
abstract class PersonalComputer{
	private Board board;
	private OS os;
	private Displayer displayer;
	
	public void setBoard(Board board) {
		this.board = board;
	}

	public void setOS(OS os) {
		this.os = os;
	}

	public void setDisplayer(Displayer displayer) {
		this.displayer = displayer;
	}

	public Board getBoard() {
		return board;
	}

	public OS getOS() {
		return os;
	}

	public Displayer getDisplayer() {
		return displayer;
	}
	
}

/*
 * 	个人电脑组装工业流水线
 * */
interface AssemblyLine{
	PersonalComputer assemble();		//组装
}

class MacBook extends PersonalComputer{
}
class DellBook extends PersonalComputer{
}

class MacBookAssemblyLine implements AssemblyLine{
	private MacBoard macBoard;
	private MacOS macOS;
	private MacDisplayer macDisplayer;
	
	public void buildMacBoard() {
		macBoard = new MacBoard();
	}
	public void buildMacOS() {
		macOS = new MacOS();
	}
	public void buildMacDisplayer() {
		macDisplayer = new MacDisplayer();
	}
	
	@Override
	public PersonalComputer assemble() {
		PersonalComputer macBook = new MacBook();
		macBook.setBoard(macBoard);
		macBook.setOS(macOS);
		macBook.setDisplayer(macDisplayer);
		return macBook;
	}
}

class DellBookAssemblyLine implements AssemblyLine{
	private WindowsOS windowsOS;
	private DellBoard dellBoard;
	private DellDisplayer dellDisplayer;
	
	public void buildDellBoard() {
		dellBoard = new DellBoard();
	}
	public void buildWindowsOS() {
		windowsOS = new WindowsOS();
	}
	public void buildDellDisplayer() {
		dellDisplayer = new DellDisplayer();
	}
	
	@Override
	public PersonalComputer assemble() {
		PersonalComputer dellBook = new DellBook();
		dellBook.setBoard(dellBoard);
		dellBook.setOS(windowsOS);
		dellBook.setDisplayer(dellDisplayer);
		return dellBook;
	}
}

/*
 * 	具体的
 * 	各类型的部件
 * */
class MacBoard implements Board{
}
class DellBoard implements Board{
}
class SuaBorad implements Board{
}

class MacOS implements OS{
}
class WindowsOS implements OS{
}

class MacDisplayer implements Displayer{
}
class DellDisplayer implements Displayer{
}
class SuaDisplayer implements Displayer{
}

