package src.main.java.storm.spouts;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.Map;

import backtype.storm.spout.SpoutOutputCollector;
import backtype.storm.task.TopologyContext;
import backtype.storm.topology.IRichSpout;
import backtype.storm.topology.OutputFieldsDeclarer;
import backtype.storm.tuple.Fields;
import backtype.storm.tuple.Values;



public class ParseLogSpout implements IRichSpout 
{
    private SpoutOutputCollector collector;
    private FileReader fileReader;
    private boolean completed = false;
    private TopologyContext context;
    @Override
    public void open(Map conf, TopologyContext context,
            SpoutOutputCollector collector) 
    {
        try {
            this.context = context;
            //this.fileReader = new FileReader(conf.get("inputFile").toString());
            String filePath= "/Users/vasu/Desktop/NASALog.txt";
            this.fileReader = new FileReader(filePath);
        } catch (FileNotFoundException e) {
            throw new RuntimeException("Error reading file "
                    + conf.get("inputFile"));
        }
        this.collector = collector;
    }
    
    @Override
    public void nextTuple() 
    {
        if (completed) {
            try {
                Thread.sleep(1000);
            } catch (InterruptedException e) {

            }
        }
        String str;
        BufferedReader reader = new BufferedReader(fileReader);
        try {
            while ((str = reader.readLine()) != null) {
                this.collector.emit(new Values(str), str);
            }
        } catch (Exception e) {
            throw new RuntimeException("Error reading tuple", e);
        } finally {
            completed = true;
        }
    }

  
    @Override
    public void declareOutputFields(OutputFieldsDeclarer declarer) 
    {
        declarer.declare(new Fields("line"));
    }
    @Override
    public void close() {
        try {
            fileReader.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }


    public boolean isDistributed() {
        return false;
    }
    @Override
    public void activate() {
    }
    @Override
    public void deactivate() {
    }
    @Override
    public void ack(Object msgId) {
    }
    @Override
    public void fail(Object msgId) {
    }
    @Override
    public Map<String, Object> getComponentConfiguration() {
        return null;
    }
}
