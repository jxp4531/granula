/*
 * Copyright 2015 Delft University of Technology
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *         http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package nl.tudelft.pds.granula.modeller.giraph.operation;

import nl.tudelft.pds.granula.archiver.entity.info.Source;
import nl.tudelft.pds.granula.archiver.entity.info.SummaryInfo;
import nl.tudelft.pds.granula.archiver.entity.operation.Operation;
import nl.tudelft.pds.granula.modeller.rule.derivation.BasicSummaryDerivation;
import nl.tudelft.pds.granula.modeller.rule.linking.IdentifierParentLinking;
import nl.tudelft.pds.granula.modeller.model.operation.ConcreteOperationModel;
import nl.tudelft.pds.granula.modeller.rule.visual.MainInfoTableVisualization;
import nl.tudelft.pds.granula.modeller.giraph.GiraphType;

import java.util.ArrayList;

public class BspWorkerZookeerpSetup extends ConcreteOperationModel {

    public BspWorkerZookeerpSetup() {
        super(GiraphType.BspWorker, GiraphType.ZookeeperSetup);
    }

    public void loadRules() {
        super.loadRules();

        addLinkingRule(new IdentifierParentLinking(GiraphType.BspWorker, GiraphType.Equal, GiraphType.Setup , GiraphType.Unique));

        addInfoDerivation(new SummaryDerivation(10));
        addVisualDerivation(new MainInfoTableVisualization(1,
                new ArrayList<String>() {{
//                    add("MasterElected");
                }}));
    }

    protected class SummaryDerivation extends BasicSummaryDerivation {

        public SummaryDerivation(int level) { super(level); }

        @Override
        public boolean execute() {
            Operation operation = (Operation) entity;
            String summary = String.format("The [%s] operation does the initial communication with Zookeeper. " +
                    "It can be rather time consuming, if a stand-alone zookeeper is created for each job. ", operation.getName());
            summary += getBasicSummary(operation);

            SummaryInfo summaryInfo = new SummaryInfo("Summary");
            summaryInfo.setValue("A Summary");
            summaryInfo.addSummary(summary, new ArrayList<Source>());
            operation.addInfo(summaryInfo);
            return  true;
        }
    }

}
