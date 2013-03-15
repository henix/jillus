package henix.jillus;

/**
 * A mark in source, you can either:
 *
 * 1. goback() to it only ONCE
 * 2. tillNow() get the string from mark to current position, only ONCE
 * 3. cancel it, then it become invalid, you can not use it to do 1. or 2.
 *
 * If you created a mark, one and only one of above 3 actions MUST be done on it.
 *
 * @see Source
 *
 * @author henix
 */
public interface Mark {
}
