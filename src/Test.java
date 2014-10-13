import java.util.concurrent.ExecutionException;
import java.util.concurrent.ForkJoinPool;
import java.util.concurrent.Future;


public class Test{
	int start = 0;
	int end = 10000;
	final static int THRESHOLD = 100;
	public Integer compute() {
		int sum = 0;
		if ((start - end) < THRESHOLD) {
			for (int i = start; i < end; i++) {
				sum += i;
			}
		} else {
			int middle = (start + end) / 2;
			Calculator left = new Calculator(start, middle);
			Calculator right = new Calculator(middle + 1, end);
			left.fork();
			right.fork();

			sum = left.join() + right.join();
		}
		return sum;
	}

    public static void main(String args[]){
    	long l = System.currentTimeMillis();
    	Test t = new Test();
    	int value = t.compute();
    	System.out.println(value);
    	System.out.println(System.currentTimeMillis()-l);
    	long x = System.currentTimeMillis();
    	ForkJoinPool forkJoinPool = new ForkJoinPool();
        Future<Integer> result = forkJoinPool.submit(new Calculator(0, 10000));

        try {
			System.out.println(result.get());
		} catch (InterruptedException e) {
			e.printStackTrace();
		} catch (ExecutionException e) {
			e.printStackTrace();
		}
        System.out.println(System.currentTimeMillis()-x);
    }

}
