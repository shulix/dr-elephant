package com.linkedin.drelephant.analysis.heuristics;

import java.io.IOException;
import java.util.HashMap;

import com.linkedin.drelephant.analysis.Constants;
import com.linkedin.drelephant.analysis.Heuristic;
import com.linkedin.drelephant.analysis.HeuristicResult;
import com.linkedin.drelephant.analysis.Severity;
import com.linkedin.drelephant.hadoop.HadoopCounterHolder;
import com.linkedin.drelephant.hadoop.HadoopCounterHolder.CounterName;
import com.linkedin.drelephant.hadoop.HadoopJobData;
import com.linkedin.drelephant.hadoop.HadoopTaskData;

import junit.framework.TestCase;


public class MapperInputSizeHeuristicTest extends TestCase {

  private static final long unitSize = Constants.HDFS_BLOCK_SIZE;
  Heuristic heuristic = new MapperInputSizeHeuristic();

  public void testLargeFileCritical() throws IOException {
    assertEquals(Severity.CRITICAL, analyzeJob(100, 5 * unitSize));
  }

  public void testLargeFileSevere() throws IOException {
    assertEquals(Severity.SEVERE, analyzeJob(200, 5 * unitSize));
  }

  public void testLargeFileModerate() throws IOException {
    assertEquals(Severity.MODERATE, analyzeJob(500, 5 * unitSize));
  }

  public void testLargeFileLow() throws IOException {
    assertEquals(Severity.LOW, analyzeJob(1000, 5 * unitSize));
  }

  public void testLargeFileNone() throws IOException {
    assertEquals(Severity.NONE, analyzeJob(2000, 5 * unitSize));
  }

  public void testSmallFileCritical() throws IOException {
    assertEquals(Severity.CRITICAL, analyzeJob(500, unitSize / 32));
  }

  public void testSmallFileSevere() throws IOException {
    assertEquals(Severity.SEVERE, analyzeJob(200, unitSize / 32));
  }

  public void testSmallFileModerate() throws IOException {
    assertEquals(Severity.MODERATE, analyzeJob(50, unitSize / 32));
  }

  public void testSmallFileLow() throws IOException {
    assertEquals(Severity.LOW, analyzeJob(10, unitSize / 32));
  }

  public void testSmallFileNone() throws IOException {
    assertEquals(Severity.NONE, analyzeJob(5, unitSize / 32));
  }

  private Severity analyzeJob(int numTasks, long inputSize) throws IOException {
    HadoopCounterHolder jobCounter = new HadoopCounterHolder(null);
    HadoopTaskData[] mappers = new HadoopTaskData[numTasks];

    HadoopCounterHolder taskCounter = new HadoopCounterHolder(new HashMap<CounterName,Long>());
    taskCounter.set(HadoopCounterHolder.CounterName.HDFS_BYTES_READ, inputSize);

    int i = 0;
    for (; i < numTasks; i++) {
      mappers[i] = new HadoopTaskData(taskCounter, new long[4]);
    }

    HadoopJobData data = new HadoopJobData().setCounters(jobCounter).setMapperData(mappers);
    HeuristicResult result = heuristic.apply(data);
    return result.getSeverity();
  }
}