package src.main.java.storm;

import src.main.java.storm.spouts.ParseLogSpout;
import src.main.java.storm.bolts.SearchImageBolt;
import src.main.java.storm.bolts.WordCounterBolt;

import backtype.storm.Config;
import backtype.storm.LocalCluster;
import backtype.storm.topology.TopologyBuilder;
import backtype.storm.utils.Utils;

public class NASATopology 
{
    public static void main(String[] args) throws Exception 
    {
        /*Config config = new Config();
        config.put("inputFile", args[0]);
        config.setDebug(true);
        config.put(Config.TOPOLOGY_MAX_SPOUT_PENDING, 1);*/

        TopologyBuilder builder = new TopologyBuilder();

        builder.setSpout( "spout", new ParseLogSpout() );
        builder.setBolt("SearchImageBolt1", new SearchImageBolt()).shuffleGrouping("spout");
        builder.setBolt("WordCounter", new WordCounterBolt()).shuffleGrouping("SearchImageBolt1");


        Config conf = new Config();
        
        LocalCluster cluster = new LocalCluster();
        cluster.submitTopology("test", conf, builder.createTopology());
        Utils.sleep(10000);        
        cluster.shutdown();
    }
}