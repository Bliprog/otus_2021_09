package cachehw;


import java.lang.ref.Reference;
import java.lang.ref.WeakReference;
import java.util.ArrayList;
import java.util.List;
import java.util.WeakHashMap;
import java.util.stream.Collectors;

public class MyCache<K, V> implements HwCache<K, V> {
    //Надо реализовать эти методы
    private final WeakHashMap<K, V> cacheStore = new WeakHashMap<>();
    private final List<WeakReference<HwListener<K, V>>> weakReferenceListeners = new ArrayList<>();

    @Override
    public void put(K key, V value) {
        cacheStore.put(key, value);
       notifyListeners(key,value,"put");
    }

    @Override
    public void remove(K key) {
        var removedValue = cacheStore.get(key);
        cacheStore.remove(key);
        notifyListeners(key,removedValue,"remove");
    }

    @Override
    public V get(K key) {
        var getValue = cacheStore.get(key);
        notifyListeners(key,getValue,"get");
        return getValue;

    }

    @Override
    public void addListener(HwListener<K, V> listener) {
        WeakReference<HwListener<K, V>> weakListener = new WeakReference<>(listener);
        weakReferenceListeners.add(weakListener);
    }

    @Override
    public void removeListener(HwListener<K, V> listener) {
        weakReferenceListeners.stream()
                .filter(wrl->wrl.get()==listener)
                .forEach(weakReferenceListeners::remove);
    }

    private void notifyListeners(K key, V value, String action){
        weakReferenceListeners.removeAll(weakReferenceListeners.stream()
                .filter(wrl->wrl.get()==null).
                collect(Collectors.toList()));
        weakReferenceListeners.forEach(wrl -> {
            var notWeakListener = wrl.get();
            if (notWeakListener != null) {
                notWeakListener.notify(key, value, action);
            }
        });
    }
}