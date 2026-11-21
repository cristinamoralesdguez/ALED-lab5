package es.upm.dit.aled.lab5;

import java.awt.Color;
import java.util.LinkedList;
import java.util.Queue;

import es.upm.dit.aled.lab5.gui.Position2D;

/**
 * Extension of Area that maintains a strict queue of the Patients waiting to
 * enter in it. After a Patient exits, the first one in the queue will be
 * allowed to enter.
 * 
 * @author rgarciacarmona
 */
public class AreaQueue extends Area {
	
	private Queue<Patient> waitQueue; // Único atributo nuevo

	public AreaQueue(String name, int time, int capacity, Position2D position) {
		super(name, time, capacity, position); 
		this.waitQueue = new LinkedList<Patient>(); // Que la cola se cree como una cola vacía en principio de tipo LinkedList
	}
	
	@Override // Porque vas a reprogramar un método de la clase padre
	public synchronized void enter(Patient p) {
		this.waiting++;
		waitQueue.add(p); // En el mismo instante que invoca si puede entrar lo dejo esperando y lo añado a la cola
		while(numPatients >= capacity || waitQueue.peek() != p) { // O le preguntas a la cola de espera a quién le toca
			try {
				wait();
			} catch (InterruptedException e) {}
			waiting++;
		}
		waiting--;
		numPatients++;
		waitQueue.remove(p); // Cuando permites a un paciente que entre al area le tienes que borrar de la cola
	}
	
}