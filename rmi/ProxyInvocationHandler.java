package rmi;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.OutputStream;
import java.io.Serializable;
import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;
import java.lang.reflect.Proxy;
import java.net.InetAddress;
import java.net.InetSocketAddress;
import java.net.Socket;

public class ProxyInvocationHandler<T> implements InvocationHandler, Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 3897015992229954650L;

	Class<T> c;
	InetAddress address;
	int port;

	public ProxyInvocationHandler(Class<T> c, InetSocketAddress address) {
		this.c = c;
		this.address = address.getAddress();
		this.port = address.getPort();

	}

	@Override
	public Object invoke(Object proxy, Method method, Object[] args) throws Throwable {

		ResponseObject responseObject = null;
		String methodName = method.getName();
		String methodReturnType = method.getReturnType().getName();
		Object objectRes = null;
		if (methodName.equals("equals") && method.getParameterTypes().length >= 1) {
			return isEqualsStub(proxy, method, args);
		}
		if (methodName.equals("hashCode") && method.getParameterTypes().length == 0)
			return hashCodeStub(proxy);
		if (methodName.equals("toString") && method.getParameterTypes().length == 0)
			return toStringStub(proxy);
		Socket clientSocket = null;

		clientSocket = new Socket(address, port);
		ObjectOutputStream objectOutputStream = null;
		ObjectInputStream objectInputStream = null;
		OutputStream outToServer = clientSocket.getOutputStream();
		InputStream inFromServer = clientSocket.getInputStream();

		DataOutputStream out = new DataOutputStream(outToServer);
		DataInputStream in = new DataInputStream(inFromServer);

		objectOutputStream = new ObjectOutputStream(out);
		objectOutputStream.flush();

		try {
			objectOutputStream.writeObject(new RequestObject(methodName, args, method.getParameterTypes()));
			objectOutputStream.flush();
		} catch (IOException e) {
			clientSocket.close();
			throw new RMIException("Can't writeObject to outStream.", e);
		}

		objectInputStream = new ObjectInputStream(in);

		try {
			responseObject = (ResponseObject) objectInputStream.readObject();
		} catch (ClassNotFoundException e1) {
			clientSocket.close();
			;
			throw new RMIException("Can't convert received object to ReturnObj.", e1);
		} catch (IOException e1) {
			clientSocket.close();
			;
			throw new RMIException("Can't read from inStream when receive from skeleton.", e1);
		}
		try {
			objectOutputStream.close();
			objectInputStream.close();

			clientSocket.close();
		} catch (Exception e) {
			throw new RMIException("can't close network I/O stream or client socket.");
		}

		objectRes = responseObject.getObjName();
		Exception exception = responseObject.getException();

		if (exception != null)
			throw exception.getCause();

		return objectRes;
	}

	@SuppressWarnings("unchecked")
	public boolean isEqualsStub(Object proxyObject, Method method, Object[] args) {

		if (args[0] == null || proxyObject == null)
			return false;

		if (!Proxy.isProxyClass(args[0].getClass()))
			return false;

		InvocationHandler handlerObj1 = Proxy.getInvocationHandler(args[0]);

		if (!(handlerObj1 instanceof ProxyInvocationHandler)) {
			return false;
		}

		ProxyInvocationHandler<T> proxyInvocationHandler = (ProxyInvocationHandler<T>) handlerObj1;
		if (proxyInvocationHandler.c == null ^ this.c == null) {
			return false;
		}

		if (proxyInvocationHandler.address == null ^ (this.address == null)) {
			return false;
		}

		if (proxyInvocationHandler.address != null && (this.address != null)) {
			if (!(proxyInvocationHandler.address).equals(this.address)
					|| (!(proxyInvocationHandler.port == (this.port))))
				return false;
		}
		if ((proxyInvocationHandler.port != (this.port)))
			return false;

		if (proxyInvocationHandler.c != null)
			if (!(proxyInvocationHandler.c.getClass().equals(this.c.getClass())))
				return false;

		return true;
	}

	public String toStringStub(Object proxy) {

		@SuppressWarnings("unchecked")
		ProxyInvocationHandler<T> stubProxy = (ProxyInvocationHandler<T>) Proxy.getInvocationHandler(proxy);

		if (stubProxy.c != null && stubProxy.address != null)
			return stubProxy.c.getName() + " " + stubProxy.address.toString() + " " + Integer.toString(stubProxy.port);
		if (stubProxy.c == null && stubProxy.address != null)
			return " " + stubProxy.address.toString() + " " + Integer.toString(stubProxy.port);
		if (stubProxy.c == null && stubProxy.address == null)
			return " " + " " + Integer.toString(stubProxy.port);
		return "";

	}

	public int hashCodeStub(Object proxy) {
		return toStringStub(proxy).hashCode();
	}

}
