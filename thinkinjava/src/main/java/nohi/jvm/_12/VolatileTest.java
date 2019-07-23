package nohi.jvm._12;

public class VolatileTest {
	public static volatile int race = 1;
	
	public static void increace(){
		race ++;
	}
	/**
	 * @param args
	 */
	public static void main(String[] args) {
		Thread[] thread = new Thread[20];
		for (int i = 0 ; i < thread.length ; i++) {
			thread[i] = new Thread(new Runnable(){
				@Override
				public void run() {
					for (int ti = 0 ; ti < 1000 ; ti ++) {
						increace();
					}
				}
			});
			
			thread[i].start();
		}
		System.out.println(Thread.activeCount());
		if (Thread.activeCount() > 1) {
			//Thread.yield();
		}
		
		System.out.println("race:" + race);
	}

}
