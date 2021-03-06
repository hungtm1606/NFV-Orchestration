package evaluation.metrics;


import java.util.HashMap;
import java.util.LinkedList;

import algorithm.EntityMapping;
import algorithm.Tuples.Tuple;
import networks.VNFChain;
import networks.VNFFG;
import vnreal.constraints.demands.CommonDemand;
import vnreal.constraints.resources.CommonResource;
import vnreal.mapping.Mapping;
import vnreal.network.substrate.SubstrateLink;
import vnreal.network.substrate.SubstrateNetwork;
import vnreal.network.substrate.SubstrateNode;

public class VNFMaxBackupUtilization extends VNFEvaluationMetric {

	public Double calculate(
			SubstrateNetwork sNet,
			HashMap<VNFFG, Tuple<VNFChain, LinkedList<EntityMapping>>> mappingResult) {
		
		Double max = null;

		for (SubstrateNode sn : sNet.getVertices()) {
			CommonResource r = (CommonResource) sn.get(CommonResource.class);
			if (r != null) {
				double thisnum = 0;
				for (Mapping m : r.getMappings()) {
					if (((CommonDemand) m.getDemand()).isBackup) {
						thisnum++;
					}
				}
				
				if (max == null || max < thisnum)
					max = thisnum;
			}
		}
		
		for (SubstrateLink sl : sNet.getEdges()) {
			CommonResource r = (CommonResource) sl.get(CommonResource.class);
			if (r != null) {
				double thisnum = 0;
				for (Mapping m : r.getMappings()) {
					if (((CommonDemand) m.getDemand()).isBackup) {
						thisnum++;
					}
				}
				
				if (max == null || max < thisnum)
					max = thisnum;
			}
		}
		
		if (max == null)
			return Double.NaN;
		
		return max;
	}

}
