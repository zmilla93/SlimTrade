package com.slimtrade.core.ninja;

import org.jnativehook.mouse.NativeMouseListener;
import org.jnativehook.mouse.NativeMouseMotionListener;

/**
 * This is a wrapper class to pass native mouse from a parent component to a child component.
 * Currently only used for NinjaStashWindow > NinjaGridPanel, should rename if used elsewhere.
 */
public interface NinjaMouseAdapter extends NativeMouseListener, NativeMouseMotionListener {

}
