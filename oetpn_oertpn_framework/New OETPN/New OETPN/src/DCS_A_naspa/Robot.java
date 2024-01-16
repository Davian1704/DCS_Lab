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

public class Robot {
	public static void main(String[] args) {
		PetriNet pn = new PetriNet();
		pn.PetriNetName = "Robot";
		pn.NetworkPort = 1080;
		
		DataFloatFloat in = new DataFloatFloat();
		in.SetName("in");
		pn.PlaceList.add(in);

		DataFloatFloat pos = new DataFloatFloat();
		pos.SetName("pos");
		pos.SetValue(new FloatFloat(0.0f, 0.0f));
		pn.PlaceList.add(pos);

		DataFloatFloat p_sum = new DataFloatFloat();
		p_sum.SetName("p_sum");
		pn.PlaceList.add(p_sum);

		DataTransfer send = new DataTransfer();
		send.SetName("send");
		send.Value = new TransferOperation("localhost", "1090","ch2");
		pn.PlaceList.add(send);


		// T0 ------------------------------------------------
		PetriTransition t0 = new PetriTransition(pn);
		t0.TransitionName = "t0";
		t0.InputPlaceName.add("in");
		t0.InputPlaceName.add("pos");

		Condition T0Ct1 = new Condition(t0, "in", TransitionCondition.NotNull);
		Condition T0Ct2 = new Condition(t0, "pos", TransitionCondition.NotNull);
		T0Ct1.SetNextCondition(LogicConnector.AND, T0Ct2);

		GuardMapping grdT0 = new GuardMapping();
		grdT0.condition = T0Ct1;
		ArrayList<String> lstInput = new ArrayList<String>();
		lstInput.add("in");
		lstInput.add("pos");
		grdT0.Activations.add(new Activation(t0, lstInput, TransitionOperation.Add_FloatFlaot, "p_sum"));

		t0.GuardMappingList.add(grdT0);
		t0.Delay = 0;
		pn.Transitions.add(t0);
		// T1 ------------------------------------------------
		PetriTransition t1 = new PetriTransition(pn);
		t1.TransitionName = "t1";
		t1.InputPlaceName.add("p_sum");

		Condition t1Ct1 = new Condition(t1, "p_sum", TransitionCondition.NotNull);

		GuardMapping grdt1 = new GuardMapping();
		grdt1.condition = t1Ct1;
		grdt1.Activations.add(new Activation(t1, "p_sum", TransitionOperation.Move, "pos"));
		grdt1.Activations.add(new Activation(t1, "p_sum", TransitionOperation.SendOverNetwork, "send"));

		t1.GuardMappingList.add(grdt1);
		t1.Delay = 1;
		pn.Transitions.add(t1);
		System.out.println("Robot started \n ------------------------------");

		pn.Delay = 2000;

		PetriNetWindow frame = new PetriNetWindow(false);
		frame.petriNet = pn;
		frame.setVisible(true);
		
	}
}
