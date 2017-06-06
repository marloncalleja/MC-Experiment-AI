package marlon.minecraftai.ai.blockmap;

public interface ChunkCubeProvider<T> {
	public T getForChunk(int chunkStartX, int chunkStartY, int chunkStartZ);
}
