package com.playground.threading.jacobjenkov;

import java.util.concurrent.Semaphore;

public class SemaphoreExample {
    private static final int MAX_AVAILABLE = 100;
    private final Semaphore available = new Semaphore(MAX_AVAILABLE, true);
    protected Object[] items = new Object[10]; // whatever kinds of items being managed
    protected boolean[] used = new boolean[MAX_AVAILABLE];

    // Not a particularly efficient data structure; just for demo

    protected synchronized Object getNextAvailableItem() {
        for (int i = 0; i < MAX_AVAILABLE; ++i) {
            if (!this.used[i]) {
                this.used[i] = true;
                return this.items[i];
            }
        }
        return null; // not reached
    }

    protected synchronized boolean markAsUnused(Object item) {
        for (int i = 0; i < MAX_AVAILABLE; ++i) {
            if (item == this.items[i]) {
                if (this.used[i]) {
                    this.used[i] = false;
                    return true;
                } else {
                    return false;
                }
            }
        }
        return false;
    }

    public Object getItem() throws InterruptedException {
        this.available.acquire();
        return getNextAvailableItem();
    }

    public void putItem(Object x) {
        if (markAsUnused(x)) {
            this.available.release();
        }
    }
}


