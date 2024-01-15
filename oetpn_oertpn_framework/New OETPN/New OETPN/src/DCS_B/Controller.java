package DCS_B;

import java.util.ArrayList;

import Components.Activation;
import Components.Condition;
import Components.GuardMapping;
import Components.PetriNet;
import Components.PetriNetWindow;
import Components.PetriTransition;
import DataObjects.DataFloat;
import DataObjects.DataTransfer;
import DataOnly.TransferOperation;
import Enumerations.LogicConnector;
import Enumerations.TransitionCondition;
import Enumerations.TransitionOperation;

public class Controller {
	public static void main(String[] args) {
		PetriNet pn = new PetriNet();
		pn.PetriNetName = "PID";
		pn.NetworkPort = 6969;

		DataFloat const8 = new DataFloat();
		const8.SetName("const8");
		const8.SetValue(0.8f);
		pn.ConstantPlaceList.add(const8);
		
		DataFloat p0 = new DataFloat();
		p0.SetName("P0");
		p0.SetValue(0.0f);
		pn.PlaceList.add(p0);
		
		DataFloat p1 = new DataFloat();
		p1.SetName("P1");
		pn.PlaceList.add(p1);
		
		DataFloat p2 = new DataFloat();
		p2.SetName("P2");
		pn.PlaceList.add(p2);
		
		DataFloat p3 = new DataFloat();
		p3.SetName("P3");
		pn.PlaceList.add(p3);
		
		DataFloat p4 = new DataFloat();
		p4.SetName("P4");
		pn.PlaceList.add(p4);
		
		DataFloat p5 = new DataFloat();
		p5.SetName("P5");
		pn.PlaceList.add(p5);
		
		DataFloat p6 = new DataFloat();
		p6.SetName("P6");
		pn.PlaceList.add(p6);
		
		DataFloat p7 = new DataFloat();
		p7.SetName("P7");
		p7.SetValue(0.0f);
		pn.PlaceList.add(p7);
		
		DataFloat p8 = new DataFloat();
		p8.SetName("P8");
		pn.PlaceList.add(p8);
		
		DataFloat p9 = new DataFloat();
		p9.SetName("P9");
		pn.PlaceList.add(p9);
		
		DataFloat p10 = new DataFloat();
		p10.SetName("P10");
		pn.PlaceList.add(p10);
		
		DataFloat p11 = new DataFloat();
		p11.SetName("P11");
		p11.SetValue(0.0f);
		pn.PlaceList.add(p11);
		
		DataFloat p12 = new DataFloat();
		p12.SetName("P12");
		p12.SetValue(0.0f);
		pn.PlaceList.add(p12);
		
		DataFloat p13 = new DataFloat();
		p13.SetName("P13");
		p13.SetValue(0.0f);
		pn.PlaceList.add(p13);
		
		DataFloat p14 = new DataFloat();
		p14.SetName("P14");
		pn.PlaceList.add(p14);
		
		DataTransfer pTrans = new DataTransfer();
		pTrans.SetName("PTrans");
		pTrans.Value = new TransferOperation("localhost", "1090","PTrans");
		pn.PlaceList.add(pTrans);
		
		// T0 ------------------------------------------------
		PetriTransition t0 = new PetriTransition(pn);
		t0.TransitionName = "t0";
		t0.InputPlaceName.add("P0");
		
		Condition T0Ct1 = new Condition(t0, "P0", TransitionCondition.NotNull);
		
		GuardMapping grdT0 = new GuardMapping();
		grdT0.condition = T0Ct1;
		grdT0.Activations.add(new Activation(t0, "P0", TransitionOperation.Move, "P1"));
		
		t0.GuardMappingList.add(grdT0);
		t0.Delay = 0;
		pn.Transitions.add(t0);
		
		// T1 ------------------------------------------------
		
		PetriTransition t1 = new PetriTransition(pn);
		t1.TransitionName = "t1";
		t1.InputPlaceName.add("P1");
		t1.InputPlaceName.add("P2");
		
		Condition t1Ct1 = new Condition(t1, "P1", TransitionCondition.NotNull);
		Condition t1Ct2 = new Condition(t1, "P2", TransitionCondition.NotNull);
		t1Ct1.SetNextCondition(LogicConnector.AND, t1Ct2);
		
		GuardMapping grdt1 = new GuardMapping();
		grdt1.condition = t1Ct1;
		grdt1.Activations.add(new Activation(t1, "P2", TransitionOperation.Move, "P3"));
		
		t1.GuardMappingList.add(grdt1);
		t1.Delay = 0;
		pn.Transitions.add(t1);
		// T2 ------------------------------------------------
		
		PetriTransition t2 = new PetriTransition(pn);
		t2.TransitionName = "t2";
		t2.InputPlaceName.add("P3");
		t2.InputPlaceName.add("P4");
		
		Condition t2Ct1 = new Condition(t2, "P3", TransitionCondition.NotNull);
		Condition t2Ct2 = new Condition(t2, "P4", TransitionCondition.NotNull);
		t2Ct1.SetNextCondition(LogicConnector.AND, t2Ct2);
		
		GuardMapping grdt2 = new GuardMapping();
		grdt2.condition = t2Ct1;
		
		ArrayList<String> lstInput = new ArrayList<String>();
		lstInput.add("P4");
		lstInput.add("P3");
		grdt2.Activations.add(new Activation(t2, lstInput, TransitionOperation.Sub, "P7"));
		grdt2.Activations.add(new Activation(t2, lstInput, TransitionOperation.Sub, "P6"));
		
		t2.GuardMappingList.add(grdt2);
		t2.Delay = 0;
		pn.Transitions.add(t2);
		// T3 ------------------------------------------------
		PetriTransition t3 = new PetriTransition(pn);
		t3.TransitionName = "t3";
		t3.InputPlaceName.add("P14");
		t3.InputPlaceName.add("P5");
		
		Condition t3Ct1 = new Condition(t3, "P14", TransitionCondition.NotNull);
		Condition t3Ct2 = new Condition(t3, "P5", TransitionCondition.NotNull);
		t3Ct1.SetNextCondition(LogicConnector.AND, t3Ct2);
		
		GuardMapping grdt3 = new GuardMapping();
		grdt3.condition = t3Ct1;
		lstInput = new ArrayList<String>();
		lstInput.add("P14");
		lstInput.add("P5");
		grdt3.Activations.add(new Activation(t3, lstInput, TransitionOperation.Add, "P8"));
		
		t3.GuardMappingList.add(grdt3);
		t3.Delay = 0;
		pn.Transitions.add(t3);
		// T4 ------------------------------------------------
		PetriTransition t4 = new PetriTransition(pn);
		t4.TransitionName = "t4";
		t4.InputPlaceName.add("P6");
		
		Condition t4Ct1 = new Condition(t4, "P6", TransitionCondition.NotNull);
		
		GuardMapping grdt4 = new GuardMapping();
		grdt4.condition = t4Ct1;
		grdt4.Activations.add(new Activation(t4, "P6", TransitionOperation.Move, "P7"));
		grdt4.Activations.add(new Activation(t4, "P6", TransitionOperation.Move, "P12"));
		
		t4.GuardMappingList.add(grdt4);
		t4.Delay = 1;
		pn.Transitions.add(t4);
		// T5 ------------------------------------------------
		PetriTransition t5 = new PetriTransition(pn);
		t5.TransitionName = "t5";
		t5.InputPlaceName.add("P8");
		t5.InputPlaceName.add("P11");
		
		Condition t5Ct1 = new Condition(t5, "P8", TransitionCondition.NotNull);
		Condition t5Ct2 = new Condition(t5, "P11", TransitionCondition.NotNull);
		t5Ct1.SetNextCondition(LogicConnector.AND, t5Ct2);
		
		GuardMapping grdt5 = new GuardMapping();
		grdt5.condition = t5Ct1;
		lstInput = new ArrayList<String>();
		lstInput.add("P8");
		lstInput.add("P11");
		grdt5.Activations.add(new Activation(t5, lstInput, TransitionOperation.Add, "P9"));
		grdt5.Activations.add(new Activation(t5, lstInput, TransitionOperation.Add, "P10"));
		
		t5.GuardMappingList.add(grdt5);
		t5.Delay = 0;
		pn.Transitions.add(t5);
		// T6 ------------------------------------------------
		PetriTransition t6 = new PetriTransition(pn);
		t6.TransitionName = "t6";
		t6.InputPlaceName.add("P10");
		
		Condition t6Ct1 = new Condition(t6, "P10", TransitionCondition.NotNull);
		
		GuardMapping grdt6 = new GuardMapping();
		grdt6.condition = t6Ct1;
		grdt6.Activations.add(new Activation(t6, "P10", TransitionOperation.Move, "P11"));
		grdt6.Activations.add(new Activation(t6, "P10", TransitionOperation.Move, "P1"));
		
		t6.GuardMappingList.add(grdt6);
		t6.Delay = 1;
		pn.Transitions.add(t6);
		// T7 ------------------------------------------------
		PetriTransition t7 = new PetriTransition(pn);
		t7.TransitionName = "t7";
		t7.InputPlaceName.add("P9");
		
		Condition t7Ct1 = new Condition(t7, "P9", TransitionCondition.NotNull);
		
		GuardMapping grdt7 = new GuardMapping();
		grdt7.condition = t7Ct1;
		grdt7.Activations.add(new Activation(t7, "P9", TransitionOperation.SendOverNetwork, "pTrans"));
		
		t7.GuardMappingList.add(grdt7);
		t7.Delay = 1;
		pn.Transitions.add(t7);
		// T8 ------------------------------------------------
		PetriTransition t8 = new PetriTransition(pn);
		t8.TransitionName = "t8";
		t8.InputPlaceName.add("P12");
		
		Condition t8Ct1 = new Condition(t8, "P12", TransitionCondition.NotNull);
		
		GuardMapping grdt8 = new GuardMapping();
		grdt8.condition = t8Ct1;
		grdt8.Activations.add(new Activation(t8, "P12", TransitionOperation.Move, "P13"));
		
		t8.GuardMappingList.add(grdt8);
		t8.Delay = 1;
		pn.Transitions.add(t8);
		// T9 ------------------------------------------------
		PetriTransition t9 = new PetriTransition(pn);
		t9.TransitionName = "t9";
		t9.InputPlaceName.add("P7");
		t9.InputPlaceName.add("P13");
		
		Condition t9Ct1 = new Condition(t9, "P7", TransitionCondition.NotNull);
		Condition t9Ct2 = new Condition(t9, "P13", TransitionCondition.NotNull);
		t9Ct1.SetNextCondition(LogicConnector.AND, t9Ct2);
		
		GuardMapping grdt9 = new GuardMapping();
		grdt9.condition = t9Ct1;
		lstInput = new ArrayList<String>();
		lstInput.add("P7");
		lstInput.add("P13");
		grdt5.Activations.add(new Activation(t9, lstInput, TransitionOperation.Add, "P8"));
		
		t9.GuardMappingList.add(grdt9);
		t9.Delay = 1;
		pn.Transitions.add(t9);
		
		System.out.println("Controller started \n ------------------------------");

		pn.Delay = 2000;

		PetriNetWindow frame = new PetriNetWindow(false);
		frame.petriNet = pn;
		frame.setVisible(true);
	}
}
