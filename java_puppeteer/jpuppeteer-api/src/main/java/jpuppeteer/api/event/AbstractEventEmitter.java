package jpuppeteer.api.event;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

public abstract class AbstractEventEmitter<E> implements EventEmitter<E> {

    private final Map<Long, AbstractListener<E>> listeners;

    public AbstractEventEmitter() {
        this.listeners = new ConcurrentHashMap<>();
    }

    protected abstract void emitInternal(AbstractListener<E> listener, E event);

    @Override
    public void emit(E event) {
        listeners.entrySet().forEach(entry -> {
            AbstractListener<E> listener = entry.getValue();
            Class<? extends E> typeClass = listener.type();
            Class<E> eventClass = (Class<E>) event.getClass();
            if (typeClass.isAssignableFrom(eventClass)) {
                //如果没有限定类型，或者限定的类型范围包含事件的实际类型才对这个监听器触发这个事件
                emitInternal(entry.getValue(), event);
            }
        });
    }

    @Override
    public void addListener(AbstractListener<? extends E> listener) {
        listeners.put(listener.id(), (AbstractListener<E>) listener);
    }

    @Override
    public void removeListener(AbstractListener<? extends E> listener) {
        listeners.remove(listener.id());
    }
}
