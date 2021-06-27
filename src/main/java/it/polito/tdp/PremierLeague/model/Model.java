package it.polito.tdp.PremierLeague.model;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

import org.jgrapht.Graph;
import org.jgrapht.Graphs;
import org.jgrapht.graph.DefaultWeightedEdge;
import org.jgrapht.graph.SimpleDirectedWeightedGraph;

import it.polito.tdp.PremierLeague.db.PremierLeagueDAO;

public class Model {
	private PremierLeagueDAO dao;
	private Graph<Player, DefaultWeightedEdge> grafo;
	private Map<Integer, Player> idMap;
	private Player topPlayer;
	private double max;
	private List<Player> dreamTeam;
	
	public Model() {
		dao=new PremierLeagueDAO();
		idMap=new HashMap<>();
	}
	
	public void creaGrafo (double media) {
		grafo=new SimpleDirectedWeightedGraph<>(DefaultWeightedEdge.class);
		dao.listAllPlayers(idMap);
		Graphs.addAllVertices(grafo, dao.getVertici(media, idMap));
		for (Adiacenza a:dao.getArchi(idMap)) {
			if (grafo.vertexSet().contains(a.getP1()) && grafo.vertexSet().contains(a.getP2())) {
				double peso=a.getPeso();
				if (peso>0) 
						Graphs.addEdgeWithVertices(grafo, a.getP1(), a.getP2(), peso);
					else if (peso<0)
						Graphs.addEdgeWithVertices(grafo, a.getP2(), a.getP1(), (-1)*peso);
			}
		}
	}
	
	public List<Sconfitti> topPlayer() {
		int max=0;
		Player top=null;
		for (Player p:grafo.vertexSet()) {
			if (grafo.outDegreeOf(p)>max) {
				max=grafo.outDegreeOf(p);
				top=p;
			}
		}
		topPlayer=top;
		List<Sconfitti> sconfitti=new LinkedList<Sconfitti>();
		for (DefaultWeightedEdge e:grafo.outgoingEdgesOf(top)) {
			sconfitti.add(new Sconfitti(grafo.getEdgeTarget(e), grafo.getEdgeWeight(e)));
		}
		Collections.sort(sconfitti);
		return sconfitti;
	}
	
	public Player getTop() {
		return topPlayer;
	}
	
	public int getNumeroVertici () {
		return grafo.vertexSet().size();
	}
	
	public int getNumeroArchi () {
		return grafo.edgeSet().size();
	}
	
	public void run (int n) {
		max=0;
		List<Sconfitti> player=new LinkedList<>();
		for (Player p:grafo.vertexSet()) {
			double pesoUscenti=0;
			double pesoEntranti=0;
			for (DefaultWeightedEdge e:grafo.outgoingEdgesOf(p)) {
				pesoUscenti+=grafo.getEdgeWeight(e);
			}
			for (DefaultWeightedEdge e:grafo.incomingEdgesOf(p)) {
				pesoEntranti+=grafo.getEdgeWeight(e);
			}
			player.add(new Sconfitti(p, pesoUscenti-pesoEntranti));
		}
		List<Player> parziale=new LinkedList<>();
		double sommaTotale=0;
		calcola(player, n, parziale, sommaTotale);
	}

	private void calcola(List<Sconfitti> player, int n, List<Player> parziale, double sommaTotale) {
		if (parziale.size()==n || player.size()==0) {
			if (sommaTotale>max) {
				max=sommaTotale;
				dreamTeam=new ArrayList<>(parziale);
			}
			return ;
		} else {
			for (Sconfitti s:player) {
				if(!parziale.contains(s.getP())) {
				double sommaTotale1=sommaTotale+s.getPeso();
				List<Sconfitti> player1=new LinkedList<>(player);
				List<Player> parziale1=new LinkedList<>(parziale);
				player1.removeAll(Graphs.successorListOf(grafo, s.getP()));
				parziale1.add(s.getP());
				calcola(player1, n, parziale1, sommaTotale1);
				}
			}
		}
		
	}
	
	public List<Player> getDreamTeam() {
		return dreamTeam;
	}
	
	public double getGrado() {
		return max;
	}
}
