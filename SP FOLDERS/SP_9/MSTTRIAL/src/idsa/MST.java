// Starter code for SP9

package idsa;

import idsa.Graph.Vertex;
import idsa.Graph.Edge;
import idsa.Graph.GraphAlgorithm;
import idsa.Graph.Factory;
import idsa.Graph.Timer;

import idsa.BinaryHeap.Index;
import idsa.BinaryHeap.IndexedHeap;
import org.w3c.dom.css.Counter;

import java.util.*;
import java.io.FileNotFoundException;
import java.io.File;

public class MST extends GraphAlgorithm<MST.MSTVertex> {
/*
we have following attributes available from graph algorithm

	public Graph g;
	Factory vf;
	Graph.Store<V> store;
*/
	String algorithm;
    public long wmst;
    List<Edge> mst;
    MST(Graph g) {
	super(g, new MSTVertex((Vertex) null));
    mst = new LinkedList<>();
    wmst = 0;
    }

    public static class MSTVertex implements Index, Comparable<MSTVertex>, Factory {

    	int componentNumer = 0;
	MSTVertex(Vertex u) {

	}

	MSTVertex(MSTVertex u) {  // for prim2
	}

	public MSTVertex make(Vertex u) { return new MSTVertex(u); }

	public void putIndex(int index) { }

	public int getIndex() { return 0; }

	public int compareTo(MSTVertex other) {
	    return 0;
	}
    }

    public void initialize(){

    	for(int i = 1; i <= g.size(); ++i){
    		get(g.getVertex(i)).componentNumer = i;
		}
   	}

    public int getCount(){
		HashSet<Integer> myset = new HashSet<>();
		for(Vertex v : this.g.getVertexArray()){
//			System.out.println(get(v).componentNumer);

			myset.add(get(v).componentNumer);
		}
		return myset.size();
	}

	public void mergeComponents(Vertex u, Vertex v){
    	printComponents();
		System.out.println(" MERGERCOMPONENETS" + u.name+ " and " + v.name);
    	int uComponent = get(u).componentNumer;
		int vComponent = get(v).componentNumer;

		for( Vertex tempVertex: g.getVertexArray()){
			if(get(tempVertex).componentNumer == uComponent){
				get(tempVertex).componentNumer = vComponent;
			}
		}
	printComponents();
	}
	public void addSafeEdges(int count){
    	Edge[] safe = new Edge[count+1];

   		for( int i = 1; i <= count-1; ++i){
   			safe[i] = null;
		}
   		Vertex u = null, v = null;
   		for(Edge e : g.getEdgeArray()){

   			u = e.fromVertex();
   			v = e.toVertex();

   			int uComponent = get(u).componentNumer;
			int vComponent = get(v).componentNumer;
   			printComponents();
   			if( uComponent != vComponent ){
				System.out.println(uComponent + " -->" + vComponent+" " + e.weight);
   				if( safe[uComponent] == null || e.compareTo(safe[uComponent]) < 0){
   					safe[uComponent] = e;
   				}

				if( safe[vComponent] == null || e.compareTo(safe[vComponent]) < 0){
					safe[vComponent] = e;
				}

			}
   		}
   			for (int i = 1; i <= count -1; ++i){
   				//System.out.println(safe[i]);
   				if(safe[i] != null)
				{
					wmst  += safe[i].weight;
					mst.add(safe[i]);

					System.out.println(safe[i]);

					System.out.println(mst);
					u = safe[i].fromVertex();
					v = safe[i].toVertex();
					int uComponent = get(u).componentNumer;
					int vComponent = get(v).componentNumer;
					System.out.println(uComponent + " -->" + vComponent+" " + safe[i].weight);

					for( Vertex tempVertex: g.getVertexArray()){
						if(get(tempVertex).componentNumer == uComponent){
							get(tempVertex).componentNumer = vComponent;
						}
					}

//					mergeComponents(u,v);
				}
			}

	}
	public void printComponents(){
		for (Vertex v : this.g.getVertexArray()) {
			System.out.println( v.name+ "COMPONENT: " + get(v).componentNumer);
		}

	}
    public long boruvka() {
	algorithm = "Boruvka";
	System.out.println("INSIDE BORUVKA");
	g.printGraph(true);
	initialize();
	int counter = getCount();
	System.out.println("count" + counter);
	wmst = 0;
	while (counter > 1) {

		System.out.println(" COUNTER " +counter);
		printComponents();
		addSafeEdges(counter);
		System.out.println(mst);
		counter = getCount();
		break;
	}
	/*
	while( count > 1){
		//AddSafeEdges(E,F,count);
		//count = countandLabel(F);
	}
*/
	return wmst;
    }

    public long prim2(Vertex s) {
	algorithm = "indexed heaps";
        mst = new LinkedList<>();
	wmst = 0;
	IndexedHeap<MSTVertex> q = new IndexedHeap<>(g.size());
	return wmst;
    }

    public static MST mst(Graph g, Vertex s, int choice) {
	MST m = new MST(g);
	switch(choice) {
	case 0:
	    m.boruvka();
	    break;
	case 1:
	    //m.prim1(s);
	    break;
	case 2:
	    m.prim2(s);
	    break;
	case 3:
	    //m.kruskal();
	    break;
	default:
	    
	    break;
	}
	return m;
    }

    public static void main(String[] args) throws FileNotFoundException {
		String string = "4 4  1 2 9   2 3 2   1 3 4   4 3 1 0";
		Scanner in;
		// If there is a command line argument, use it as file from which
		// input is read, otherwise use input from string.
		in = args.length > 0 ? new Scanner(new File(args[0])) : new Scanner(string);

		// Read graph from input
	int choice = 0;  // prim3

	if (args.length > 1) { choice = Integer.parseInt(args[1]); }

	Graph g = Graph.readGraph(in);
		g.printGraph(true);
//iterate throught all the vertexes


		Vertex s = g.getVertex(1);
		Vertex[] allVertices = g.getVertexArray();

		for( Vertex v: allVertices){
			System.out.println(v);
		}
	Timer timer = new Timer();
	MST m = mst(g, s, choice);
	System.out.println(m.algorithm + "\n" + m.wmst);
	System.out.println(timer.end());
    }
}
