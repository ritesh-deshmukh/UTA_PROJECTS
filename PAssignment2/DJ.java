//PAssignment2
//Name: Ritesh Deshmukh


import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;

public class DJ
{
    
    static int Vx=0;
    static int numLines=0;
    static Boolean[][] overAllPath=new Boolean[][]{};
    int minDistance(int dist[], Boolean spath[])
    {
        int x = Integer.MAX_VALUE, index=-1;
 
        for (int var = 0; var < Vx; var++)
            if (spath[var] == false && dist[var] <= x)
            {
                x = dist[var];
                index = var;
            }
 
        return index;
    }
 
    // Printing the distances from the source to output.txt file
    void execPrint(int distance[], int a, int og[][])
    {	
    	String[] connectedNodes=new String[numLines+1];
    	int k=1;
    	connectedNodes[0]="0";
       
        for(int i=0;i<Vx;i++){
			for(int j=0;j<Vx;j++){
				if(overAllPath[i][j]){
					connectedNodes[k++]=i+" "+j;
				}
			}
		}
        for(int l=1;l<connectedNodes.length;l++){
        	for(int m=1;m<connectedNodes.length;m++){
        		if(!connectedNodes[l].substring(0,1).equals("0")){
        			if(connectedNodes[m].substring(0,1).equals("0") && connectedNodes[m].substring(2).equals(connectedNodes[l].substring(0,1))){
        				connectedNodes[l]="0 " +connectedNodes[l];
        			}
        		}
        	}
        }
        for(int l=0;l<connectedNodes.length;l++){
        	int size=connectedNodes[l].length();
        	int index=Integer.parseInt(connectedNodes[l].substring(size-1));
        	if(index!=l){
        		String temp=connectedNodes[index];
        		connectedNodes[index]=connectedNodes[l];
        		connectedNodes[l]=temp;
        	}
        	
        }
        
        	try {
    			File file = new File("output.txt");
    			if (!file.exists()) {
    				file.createNewFile();
    			}

    			FileWriter fw = new FileWriter(file.getAbsoluteFile());
    			BufferedWriter bw = new BufferedWriter(fw);
    			for (int i = 0; i < Vx; i++){
    				bw.write(connectedNodes[i].replace(" ", "-")+" : "+distance[i]+"\r\n");
    			}
    			
    			bw.close();


    		} catch (IOException e) {
    			e.printStackTrace();
    		}
           
       
    }
 
    
    void dijkstra(int graph[][], int src)
    {
        int distance[] = new int[Vx]; 
 
        // If the node is included, the value of spath will be true
        Boolean spath[] = new Boolean[Vx];
 
        for (int i = 0; i < Vx; i++)
        {
            distance[i] = Integer.MAX_VALUE;
            spath[i] = false;
        }
 
        // Distance from source node to itself is 0
        distance[src] = 0;
 
        // Finding the shortest path for all nodes in the graph
        for (int count = 0; count < Vx-1; count++)
        {
            int v1 = minDistance(distance, spath);
 
            // Visited node will be true
            spath[v1] = true;
            
            for (int v2 = 0; v2 < Vx; v2++)
 
                if (!spath[v2] && graph[v1][v2]!=0 &&
                        distance[v1] != Integer.MAX_VALUE &&
                        distance[v1]+graph[v1][v2] < distance[v2]){
                	overAllPath[v1][v2]=true;
                	numLines=numLines+1;
                    distance[v2] = distance[v1] + graph[v1][v2];
                }
        }
 
        execPrint(distance, Vx, graph);
    }
 
    public static void main (String[] args)
    {
        //making the graph using input.txt file
    	BufferedReader br = null;
    		int distance[][]=new int[][]{};
		try {

			String sCurrentLine;

			br = new BufferedReader(new FileReader("input.txt"));
			int numOfNodes=0;
			while ((sCurrentLine = br.readLine()) != null) {
				if(numOfNodes==0){
					
					numOfNodes=Integer.parseInt(sCurrentLine);
					Vx=numOfNodes;
					distance=new int[numOfNodes][numOfNodes];
					overAllPath=new Boolean[numOfNodes][numOfNodes];

					for(int i=0;i<numOfNodes;i++){
						for(int j=0;j<numOfNodes;j++){
							distance[i][j]=0;
							overAllPath[i][j]=false;
						}
					}
				}else{
					String matrix=sCurrentLine.substring(0,sCurrentLine.indexOf(" "));
					String indexes[]=matrix.split(",");
					distance[Integer.parseInt(indexes[0])][Integer.parseInt(indexes[1])]=
							Integer.parseInt(sCurrentLine.substring(sCurrentLine.indexOf(" ")).trim());
				}
			
			}
			

		} catch (IOException e) {
			e.printStackTrace();
		} finally {
			try {
				if (br != null)br.close();
			} catch (IOException ex) {
				ex.printStackTrace();
			}
		}
                
        DJ pass = new DJ();
        pass.dijkstra(distance, 0);
    }
}