package src.main.java.storm.bolts;

import backtype.storm.task.OutputCollector;
import backtype.storm.task.TopologyContext;
import backtype.storm.topology.OutputFieldsDeclarer;
import backtype.storm.topology.base.BaseRichBolt;
import backtype.storm.tuple.Fields;
import backtype.storm.tuple.Tuple;
import backtype.storm.tuple.Values;
import backtype.storm.utils.Utils;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

public class SearchImageBolt extends BaseRichBolt 
{
    private OutputCollector collector;

    public void prepare( Map conf, TopologyContext context, OutputCollector collector ) 
    {
        this.collector = collector;
    }

    public void execute( Tuple tuple ) 
    {
        String sentence = tuple.getString(0);
        String[] words = sentence.split(" ");
        for(String word: words){
            word = word.trim();
            if(!word.isEmpty() && word.contains(".gif")){
                collector.emit(new Values(word));                
            }
        }
        collector.ack( tuple );
    }

    public void declareOutputFields( OutputFieldsDeclarer declarer ) 
    {
        declarer.declare(new Fields("word"));
    }   
}