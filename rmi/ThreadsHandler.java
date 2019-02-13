package rmi;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;

public class ThreadsHandler<T> extends Thread {

	ServerSocket serverSocket;
	Skeleton<T> skeletonInstance;
	ExecutorService pool;
	Class<T> c;
	T server;
	ExecutorService executorThread = Executors.newCachedThreadPool();

	public ThreadsHandler(Skeleton<T> skeleton, Class<T> c, T server, ServerSocket serverSocket) {

		this.c = c;
		this.serverSocket = serverSocket;
		this.skeletonInstance = skeleton;
		this.server = server;
		this.executorThread = Executors.newFixedThreadPool(10);

	}

	@Override
	public void run() {
		while (this.skeletonInstance.isServerStarted) {

			try {
				Socket clientSocket = null;
				if (!serverSocket.isClosed())
					clientSocket = serverSocket.accept();
				if (this.skeletonInstance.isServerStarted())
					this.executorThread.execute(new WorkerThreads<T>(c, skeletonInstance, clientSocket, server));

			} catch (Exception e) {

				e.printStackTrace();
				if (this.skeletonInstance.isServerStarted) {
					boolean cont = this.skeletonInstance.listen_error(e);
					if (cont)
						continue;
					else {
						this.skeletonInstance.stop();
						this.skeletonInstance.stopped(e);
					}
				}

				return;
			}
		}
		this.executorThread.shutdown();
		try {
			this.executorThread.awaitTermination(1, TimeUnit.MINUTES);
		} catch (InterruptedException ex) {
			ex.printStackTrace();
		}

		try {
			this.serverSocket.close();
		} catch (IOException e) {

			e.printStackTrace();
		}
		this.skeletonInstance.stopped(new Throwable());
	}

}
