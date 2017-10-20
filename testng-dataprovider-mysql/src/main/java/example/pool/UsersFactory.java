package example.pool;

import example.model.UsersEntity;
import org.vibur.objectpool.PoolObjectFactory;

import java.util.List;
import java.util.concurrent.atomic.AtomicInteger;

public class UsersFactory implements PoolObjectFactory<UsersEntity> {
	private final List<UsersEntity> users;
	private final AtomicInteger counter;

	public UsersFactory(final List<UsersEntity> users) {
		this.users = users;
		this.counter = new AtomicInteger(users.size() - 1);
	}

	@Override
	public UsersEntity create() {
		return users.get(counter.getAndDecrement());
	}

	@Override
	public boolean readyToTake(UsersEntity obj) {
		return true;
	}

	@Override
	public boolean readyToRestore(UsersEntity obj) {
		return true;
	}

	@Override
	public void destroy(UsersEntity obj) {
		users.remove(obj);
	}
}
