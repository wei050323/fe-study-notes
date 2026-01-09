using System.Collections;
using System.Collections.Generic;
using System.ComponentModel;
using UnityEngine;

public enum GameState
{
    Idle, //没有顾客
    CustomerArrive,//顾客到达
    Ordering,//点餐
    Making,//制作(面团->酱料->配料)
    Done, 
}
public class GameManager : MonoBehaviour
{
    public static GameManager Instance;
    public GameState CurrentState;
    private void Awake()
    {
        Instance = this;
        CurrentState = GameState.Idle;
    }
    public void ChangeState(GameState newState)
    {
        CurrentState = newState;
        Debug.Log("Game State Changed to: " + newState.ToString());
    }
}
