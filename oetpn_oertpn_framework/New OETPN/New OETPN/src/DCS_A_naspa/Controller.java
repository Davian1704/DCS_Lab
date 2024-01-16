package DCS_A_naspa;

import java.util.ArrayList;

import Components.Activation;
import Components.Condition;
import Components.GuardMapping;
import Components.PetriNet;
import Components.PetriNetWindow;
import Components.PetriTransition;
import DataObjects.DataFloatFloat;
import DataObjects.DataTransfer;
import DataOnly.FloatFloat;
import DataOnly.TransferOperation;
import Enumerations.LogicConnector;
import Enumerations.TransitionCondition;
import Enumerations.TransitionOperation;

public class Controller {
	public static void main(String[] args) {
		PetriNet pn = new PetriNet();
		pn.PetriNetName = "Controller";
		pn.NetworkPort = 1090;

		DataFloatFloat ch1 = new DataFloatFloat();
		ch1.SetName("ch1");
		pn.PlaceList.add(ch1);

		DataFloatFloat ch2 = new DataFloatFloat();
		ch2.SetName("ch2");
		ch2.SetValue(new FloatFloat(0.0f, 0.0f));
		pn.PlaceList.add(ch2);

		DataFloatFloat p_sum = new DataFloatFloat();
		p_sum.SetName("p_sum");
		pn.PlaceList.add(p_sum);

		DataTransfer send = new DataTransfer();
		send.SetName("send");
		send.Value = new TransferOperation("localhost", "1080","in");
		pn.PlaceList.add(send);

		// ----------Constant values to fuck yourself------------
		DataFloatFloat Const1 = new DataFloatFloat();
		Const1.SetName("Const1");
		Const1.SetValue(new FloatFloat(0.0f, 0.0f));
		pn.ConstantPlaceList.add(Const1);
		
		DataFloatFloat Const2 = new DataFloatFloat();
		Const2.SetName("Const2");
		Const2.SetValue(new FloatFloat(8.0f, 8.0f));
		pn.ConstantPlaceList.add(Const2);

		// T0 ------------------------------------------------
		PetriTransition t0 = new PetriTransition(pn);
		t0.TransitionName = "t0";
		t0.InputPlaceName.add("ch1");
		t0.InputPlaceName.add("ch2");

		Condition T0Ct1 = new Condition(t0, "ch1", TransitionCondition.NotNull);
		Condition T0Ct2 = new Condition(t0, "ch2", TransitionCondition.NotNull);
		T0Ct1.SetNextCondition(LogicConnector.AND, T0Ct2);

		GuardMapping grdT0 = new GuardMapping();
		grdT0.condition = T0Ct1;
		ArrayList<String> lstInput = new ArrayList<String>();
		lstInput.add("ch1");
		lstInput.add("ch2");
		grdT0.Activations.add(new Activation(t0, lstInput, TransitionOperation.Add_FloatFlaot, "p_sum"));
		grdT0.Activations.add(new Activation(t0, "ch1", TransitionOperation.Move, "ch1"));

		t0.GuardMappingList.add(grdT0);
		t0.Delay = 0;
		pn.Transitions.add(t0);
		// T1 ------------------------------------------------
		PetriTransition t1 = new PetriTransition(pn);
		t1.TransitionName = "t1";
		t1.InputPlaceName.add("ch1");
		t1.InputPlaceName.add("p_sum");

		Condition t1Ct1 = new Condition(t1, "p_sum", TransitionCondition.LessThan_FloatFloat, "Const2");
		Condition t1Ct2 = new Condition(t1, "p_sum", TransitionCondition.MoreThanOrEqual_FloatFloat, "Const1");
		t1Ct1.SetNextCondition(LogicConnector.AND, t1Ct2);

		GuardMapping grdt1 = new GuardMapping();
		grdt1.condition = t1Ct1;
		grdt1.Activations.add(new Activation(t1, "p_sum", TransitionOperation.MakeNull, "p_sum"));
		grdt1.Activations.add(new Activation(t1, "ch1", TransitionOperation.SendOverNetwork, "send"));

		t1.GuardMappingList.add(grdt1);
		t1.Delay = 0;
		pn.Transitions.add(t1);
		System.out.println("Controller started \n ------------------------------");

		pn.Delay = 2000;

		PetriNetWindow frame = new PetriNetWindow(false);
		frame.petriNet = pn;
		frame.setVisible(true);
	}
}
